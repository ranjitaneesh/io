package com.demia.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CountDownLatch;

/**
 * Manages a unidirectional pipe of data flowing from an OutputStream to an InputStream
 *
 * <p> A pipe consists of a writer and a reader. 
 * Once some bytes are written to the output stream they can be read
 * from input stream in the order in which they were written.
 *
 * <p> This pipe implementations will buffer up to 1024 bytes by default, however one can specify 
 * a custom buffer size as well </p>
 *  
 * 
 * @author Ranjit Aneesh
 *
 */
public abstract class Pipe {
    PipedOutputStream out;
    PipedInputStream in;
    Thread readerThread;
    Thread writerThread;
    CountDownLatch awaitReaderToDie = new CountDownLatch(1);
    
    public Pipe() {
        this(1024);
    }
    

    public Pipe(int bufferSize) {
        in = new PipedInputStream();
        try {
            out = new PipedOutputStream(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void start() {
        readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                reader(in);
                awaitReaderToDie.countDown();

            }
        });

        writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                writer(out);
                try {
                    awaitReaderToDie.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                

            }
        });
        readerThread.start();
        writerThread.start();

    }

    protected abstract void writer(OutputStream out);

    protected abstract void reader(InputStream in);

}
