package com.example.client;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    static RadioGroup radioGroup;
    static RadioButton radioButton;
    static TextView textView;
    static AnyChartView pieChart;
    static Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.text_view_selected);
        pieChart = findViewById(R.id.piechart);

        Button buttonApply = findViewById(R.id.button_apply);

        pie = AnyChart.pie();
        List<DataEntry> value = new ArrayList<>();
        value.add(new ValueDataEntry("Corona Chart",100));


        pie.data(value);
        pieChart.setChart(pie);

        buttonApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                textView.setText("Prikaz: " + radioButton.getText());

                BackgroundTask bt = new BackgroundTask(MainActivity.this);
                bt.execute();
            }
        });

    }

    public static void parseXML(final Context context)
    {
        XmlPullParserFactory parserFactory;
        try
        {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = context.openFileInput("data1.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is,null);
            processParsing(parser);
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        ArrayList<Country> countries = new ArrayList<>();
        int eventType = parser.getEventType();
        Country currentCountry =null;

        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            String eltName = null;

            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("country".equals(eltName)) {
                        currentCountry = new Country();
                        countries.add(currentCountry);
                    }
                    else if (currentCountry != null)
                    {
                        if ("name".equals(eltName))
                        {
                            currentCountry.setName(parser.nextText());
                        }
                        else if ("oboleli".equals(eltName))
                        {
                            currentCountry.setOboleli(Integer.parseInt(parser.nextText()));
                        }
                        else if ("preminuli".equals(eltName))
                        {
                            currentCountry.setPreminuli(Integer.parseInt(parser.nextText()));
                        }
                        else if ("izleceni".equals(eltName))
                        {
                            currentCountry.setIzleceni(Integer.parseInt(parser.nextText()));
                        }
                    }
                    break;
            }
            eventType=parser.next();
        }

        switch (radioGroup.getCheckedRadioButtonId())
        {
            case R.id.radio_one:

                Collections.sort(countries, new Comparator<Country>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Country o1, Country o2)
                    {
                        return Integer.compare(o2.getOboleli(), o1.getOboleli());
                    }
                });
                printOboleli(countries);
                break;


            case R.id.radio_two:

                Collections.sort(countries, new Comparator<Country>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Country o1, Country o2)
                    {
                        return Integer.compare(o2.getPreminuli(), o1.getPreminuli());

                    }
                });
                printPreminuli(countries);
                break;


            case R.id.radio_three:
                Collections.sort(countries, new Comparator<Country>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Country o1, Country o2)
                    {
                        return Integer.compare(o2.getIzleceni(), o1.getIzleceni());

                    }
                });
                printIzleceni(countries);
                break;
        }
    }

    private static void printOboleli(ArrayList<Country>countries)
    {
        List<DataEntry> value = new ArrayList<>();

        for (int i=0; i<5 ;i++)
        {
            value.add(new ValueDataEntry(countries.get(i).getName(), countries.get(i).getOboleli()));
        }
        pie.data(value);
    }

    private static void printPreminuli(ArrayList<Country>countries)
    {
        List<DataEntry> value = new ArrayList<>();

        for (int i=0; i<5 ;i++)
        {
            value.add(new ValueDataEntry(countries.get(i).getName(), countries.get(i).getPreminuli()));
        }
        pie.data(value);
    }
    private static void printIzleceni(ArrayList<Country>countries)
    {
        List<DataEntry> value = new ArrayList<>();

        for (int i=0; i<5 ;i++)
        {
            value.add(new ValueDataEntry(countries.get(i).getName(), countries.get(i).getIzleceni()));
        }
        pie.data(value);
    }

    public void checkButton(View v) throws IOException {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selektovano: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();
    }
}
