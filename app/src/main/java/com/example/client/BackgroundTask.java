package com.example.client;

import android.content.Context;
import android.os.AsyncTask;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import static android.content.Context.MODE_PRIVATE;

public class BackgroundTask extends AsyncTask<Void,Void,Void>
{
    String SERVER_IP = "192.168.0.103";
    int SERVER_PORT = 12345;
    OutputStream out = null;
    InputStream in = null;
    byte[] buffer = new byte[4096];
    String fileName ="data1.xml";
    String str;
    Context context;


    public BackgroundTask(Context context)
    {
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
    try
    {
        Socket s= new Socket(SERVER_IP,SERVER_PORT);
        out=s.getOutputStream();
        out.write('0');
        out.flush();

        in=s.getInputStream();

        in.read(buffer);
        str = new String(buffer).trim();
        save(str.getBytes());

        out.close();
        s.close();

    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    return null;
    }

    public void save (byte[] s)
    {

        FileOutputStream fos = null;
        try
        {
            fos = context.openFileOutput("data1.xml", MODE_PRIVATE);
            fos.write(s);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) { //izvrsi background i postavi nesto(uradi nesto)
        super.onPostExecute(aVoid);
        MainActivity.parseXML(context);
    }
}
