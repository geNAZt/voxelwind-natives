package net.md_5.bungee;

import net.md_5.bungee.jni.cipher.NativeCipher;
import net.md_5.bungee.jni.cipher.JavaCipher;
import net.md_5.bungee.jni.cipher.BungeeCipher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import net.md_5.bungee.jni.NativeCode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NativeCipherTest
{

    private final byte[] plainBytes = "Truly, a human menace".getBytes();
    private final byte[] cipheredBytes = DatatypeConverter.parseHexBinary("88f8ab423064f69f873f4e1773ec9a49ebde9da97b");
    private final byte[] iv = new byte[ 16 ];
    private final SecretKey secret = new SecretKeySpec( new byte[ 32 ], "AES" );
    private static final int BENCHMARK_COUNT = 4096;
    //
    private static final NativeCode<BungeeCipher> factory = new NativeCode( "native-cipher", JavaCipher.class, NativeCipher.class );

    @Test
    public void testOpenSSL() throws Exception
    {
        if ( NativeCode.isSupported() )
        {
            boolean loaded = factory.load();
            Assert.assertTrue( "Native cipher failed to load!", loaded );

            NativeCipher cipher = new NativeCipher();
            System.out.println( "Testing OpenSSL cipher..." );
            testACipher( cipher );
        }
    }

    @Test
    public void testOpenSSLBenchmark() throws Exception
    {
        if ( NativeCode.isSupported() )
        {
            boolean loaded = factory.load();
            Assert.assertTrue( "Native cipher failed to load!", loaded );

            NativeCipher cipher = new NativeCipher();

            System.out.println( "Benchmarking OpenSSL cipher..." );
            testBenchmark( cipher );
        }
    }

    @Test
    public void testJDK() throws Exception
    {
        // Create JDK cipher
        BungeeCipher cipher = new JavaCipher();

        System.out.println( "Testing Java cipher..." );
        testACipher( cipher );
    }

    @Test
    public void testJDKBenchmark() throws Exception
    {
        // Create JDK cipher
        BungeeCipher cipher = new JavaCipher();

        System.out.println( "Benchmarking Java cipher..." );
        testBenchmark( cipher );
    }

    /**
     * Hackish test which can test both native and fallback ciphers using direct
     * buffers.
     */
    public void testACipher(BungeeCipher cipher) throws Exception
    {
        // Create input buf
        ByteBuf nativePlain = Unpooled.directBuffer( plainBytes.length );
        nativePlain.writeBytes( plainBytes );
        // Create expected buf
        ByteBuf nativeCiphered = Unpooled.directBuffer( cipheredBytes.length );
        nativeCiphered.writeBytes( cipheredBytes );
        // Create output buf
        ByteBuf out = Unpooled.directBuffer( plainBytes.length );

        // Encrypt
        cipher.init( true, secret, iv );
        cipher.cipher( nativePlain, out );

        Assert.assertEquals( nativeCiphered, out );

        out.clear();

        // Decrypt
        cipher.init( false, secret, iv );
        cipher.cipher( nativeCiphered, out );
        nativePlain.resetReaderIndex();
        Assert.assertEquals( nativePlain, out );

        System.out.println( "This cipher works correctly!" );
    }

    public void testBenchmark(BungeeCipher cipher) throws Exception
    {
        // Create input buf
        byte[] random = new byte[ 1 << 12 ];
        new Random().nextBytes( random );
        ByteBuf nativePlain = Unpooled.directBuffer();
        nativePlain.writeBytes( random );

        // Create output buf
        ByteBuf nativeCiphered = Unpooled.directBuffer( plainBytes.length );

        // Encrypt
        cipher.init( true, secret, iv );
        long start = System.currentTimeMillis();
        for ( int i = 0; i < BENCHMARK_COUNT; i++ )
        {
            nativeCiphered.clear();
            cipher.cipher( nativePlain, nativeCiphered );
            nativePlain.readerIndex( 0 );
        }
        System.out.println( String.format( "Encryption Iteration: %d, Elapsed: %d ms", BENCHMARK_COUNT, System.currentTimeMillis() - start ) );

        // Create output buf
        ByteBuf out = Unpooled.directBuffer( plainBytes.length );

        // Decrypt
        cipher.init( false, secret, iv );
        start = System.currentTimeMillis();
        for ( int i = 0; i < BENCHMARK_COUNT; i++ )
        {
            cipher.cipher( nativeCiphered, out );
            nativeCiphered.readerIndex( 0 );
            out.clear();
        }
        System.out.println( String.format( "Decryption Iteration: %d, Elapsed: %d ms", BENCHMARK_COUNT, System.currentTimeMillis() - start ) );
    }
}
