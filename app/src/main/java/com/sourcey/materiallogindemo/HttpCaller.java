package com.sourcey.materiallogindemo;


import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCaller implements Runnable
{
    protected String lineEnd = "\r\n";
    protected String twoHyphens = "--";
    protected String boundary = "*****";
    //protected int maxBufferSize=1*1024*1024,bytesAvailable,bufferSize,bytesRead;
    protected String[] http_field;
    protected Bitmap files;
    public int resp;
    public String response = "";
    private int counter=0;
    private HttpURLConnection conn;
    private URL url;
    private PrintWriter writer;
    private OutputStream out;
    private String format = "";
    //private int[] page_no;
    private String urls;
    private String http_value[];

    public void starter(String[] field,Bitmap f,String[] value,String fmt,String url)
    {
        try
        {
            this.files = f;
            this.http_field = field;
            this.http_value = value;
            this.format = fmt;
            this.urls = url;
            Thread thread = new Thread(this);
            thread.start();
            thread.join();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try
        {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Number", ""+filename.length);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            out = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            if(http_field!=null) {
                for (int i = 0; i < http_field.length; i++) {
                    addField(http_field[i], http_value[i]);
                }
            }
            if(files!=null) {
                addImage(files, "pic", http_value[0] + format);
            }
            writer.append(twoHyphens+boundary+twoHyphens+lineEnd);
            writer.flush();
            out.close();
            writer.close();
            resp = conn.getResponseCode();
            if(resp== HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while((line = in.readLine())!=null){
                    response+=line;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void addImage(Bitmap bit, String name, String filename)
    {
        try{
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.PNG ,100,bout);
            byte[] bytes = bout.toByteArray();
            writer.append(twoHyphens+boundary).append(lineEnd);
            writer.append("Content-Disposition: form-data; name=\""+name+"\";filename=\""+filename+".png\"").append(lineEnd);
            this.counter++;
            writer.append("Content-Type: "+conn.guessContentTypeFromName(name)).append(lineEnd);
            writer.append("Content-Transfer-Encoding: binary").append(lineEnd);
            writer.append(lineEnd);
            writer.flush();
            out.write(bytes);
            writer.append(lineEnd);
            writer.flush();
            bout.close();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addField(String name,String value)
    {
        try{
            writer.append(twoHyphens+boundary).append(lineEnd);
            writer.append("Content-Disposition: form-data; name=\""+name+"\"").append(lineEnd);
            writer.append("Content-Type: text/plain; charset=UTF-8").append(lineEnd);
            writer.append(lineEnd);
            writer.append(value).append(lineEnd);
            writer.flush();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addFile(File F, String name)
    {
        try
        {
            writer.append(twoHyphens+boundary).append(lineEnd);
            writer.append("Content-Disposition: form-data; name=\"file"+(this.counter++)+"\";filename=\""+name+"\"").append(lineEnd);
            writer.append("Content-Type: "+conn.guessContentTypeFromName(name)).append(lineEnd);
            writer.append("Content-Transfer-Encoding: binary").append(lineEnd);
            writer.append(lineEnd);
            writer.flush();
            FileInputStream fileInputStream = new FileInputStream(F);
            int bytesRead=0;
            byte[] bytes = new byte[4096];
            while((bytesRead=fileInputStream.read(bytes))!=-1)
            {
                out.write(bytes,0,bytesRead);
            }
            out.flush();
            fileInputStream.close();
            writer.append(lineEnd);
            writer.flush();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }}