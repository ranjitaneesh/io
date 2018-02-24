# Using Pipes to transfer data in Java
I struggled with some problems which took me a while to understand and tackle. I thought of creating this project to dump some utilities that could help someone else who encounter similar situations as myself!

The project for now has just one class called Pipe.
Pipe manages a unidirectional pipe of data flowing from an OutputStream to an InputStream.
A pipe consists of a writer and a reader. Once some bytes are written to the output stream they can be read from the input stream in the order in which they were written.
This pipe implementations has a default buffer size of 1024 bytes, however one can specify a custom buffer size as well.

Below is a Test code I wrote in a hurry to demonstrate how the Pipe class really works. Note that the Pipe class has two methods- writer(OutputStream out) and reader(InputStream in) that are abstract and must be implemented where the Pipe is used. The implementing class must use the writer method's out argument to write data to the pipe. This data is then made available from the InputStream argument of the reader method. Don't forget to close the output stream once all data have been written to the pipe. See the below code for a demonstration of its usage.

    package com.demia.io.test;

    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;

    import com.demia.io.Pipe;


    public class TestPipe extends Pipe {

        public static void main(String[] args) {
            TestPipe p = new TestPipe();
            p.start();
       }

        @Override
        protected void reader(InputStream in) {
            try {
                int i = in.read();
                while (i != -1) {
                    System.out.println(i);
                    i = in.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void writer(OutputStream out) {
            try {
                out.write("abc".getBytes());
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        }

