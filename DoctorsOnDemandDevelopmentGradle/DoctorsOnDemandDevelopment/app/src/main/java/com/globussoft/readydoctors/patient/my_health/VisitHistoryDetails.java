package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GLB-276 on 1/29/2016.
 */
public class VisitHistoryDetails extends Activity
{
    TextView time, patient,doctor, status;
    ImageView backImage;
    Button printReport,makeAcall;
    String givenStartDateTimeString,givenEndDateTimeString;
    long startTime,endTime,currentTime;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointment_details);
        initUI();

    }

    private void initUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        time = (TextView) findViewById(R.id.time);
        patient = (TextView) findViewById(R.id.patient);
        doctor = (TextView) findViewById(R.id.doctor);
        status = (TextView) findViewById(R.id.status);
        status.setVisibility(View.INVISIBLE);
        System.out.println("=================Appointment Details==================");
        System.out.println(Singleton.Name);
        System.out.println(AppData.visitHistoryModel.getPatientName());
        System.out.println(AppData.visitHistoryModel.getAppointment_start_time()+"   To   "+AppData.visitHistoryModel.getAppointment_end_time());
        System.out.println(AppData.visitHistoryModel.getDoctorName());
        System.out.println("=================Appointment Details=================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        patient.setText(AppData.visitHistoryModel.getPatientName());
        doctor.setText(AppData.visitHistoryModel.getDoctorName());
        try
        {
            time.setText((givenStartDateTimeString=ConvertUtcToLocal(AppData.visitHistoryModel.getAppointment_start_time()))+"   To   "+(givenEndDateTimeString=ConvertUtcToLocal(AppData.visitHistoryModel.getAppointment_end_time())));
        }catch (ParseException e)
        {
            System.out.println("gone......>"+e);
        }



        printReport=(Button)findViewById(R.id.cancel);
        printReport.setVisibility(View.VISIBLE);
        printReport.setText("Generate Report");
        printReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"genarating report..",Toast.LENGTH_SHORT).show();
                createPDF();
            }
        });
        makeAcall=(Button)findViewById(R.id.callDoctor);
        makeAcall.setVisibility(View.INVISIBLE);

    }

    public String ConvertUtcToLocal(String originalUTCTime) throws ParseException
    {
        System.out.print("Time in UTC <"+ originalUTCTime+">");

        String format = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(originalUTCTime.trim());

        long utcStamp = date.getTime();

        // Add/Substract Zone offset into UTC time
        long localTimeStamp = utcStamp + Calendar.getInstance().get(Calendar.ZONE_OFFSET);

        Timestamp timestamp = new Timestamp(localTimeStamp);

        Date finalLocatDate = new Date(timestamp.getTime());

        String finalLocalDateString = sdf.format(finalLocatDate);

        System.out.print("finalLocalDateString   " + finalLocalDateString);

        return finalLocalDateString;
    }
    public void genareteReport()
    {

    }
    public void createPDF()
    {
        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            fileName="ReadyDoctor-"+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".pdf";
            System.out.println("formed filename : "+fileName);
            File file = new File(dir, fileName);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            /* Inserting Image in PDF*/
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.logo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);
//            myImg.setBackgroundColor(harmony.java.awt.Color.WHITE);

            //add image to document
            doc.add(myImg);

            String name="Name : "+AppData.visitHistoryModel.getPatientName();
			/* Create Paragraph and Set Font*/
            Paragraph p1 = new Paragraph(name);
			/* Create Set Font and its Size*/
            Font paraFont= new Font(Font.HELVETICA);
            paraFont.setSize(16);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

            String address="Address : "+AppData.visitHistoryModel.getPatientAddress();
            Paragraph p2 = new Paragraph(address);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont2= new Font(Font.COURIER,14.0f, Color.GREEN);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont2);

            doc.add(p2);

            String dob="Date of Birth : "+AppData.visitHistoryModel.getPatientDateOfBirth();
            Paragraph p3 = new Paragraph(dob);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont3= new Font(Font.COURIER,14.0f, Color.GREEN);
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            p3.setFont(paraFont3);

            doc.add(p3);

            String appId="Appointment Id : "+AppData.visitHistoryModel.getAppointmentId();
            Paragraph p4 = new Paragraph(appId);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont4= new Font(Font.COURIER,14.0f, Color.GREEN);
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            p4.setFont(paraFont4);

            doc.add(p4);

            String docName="Doctor Name : "+AppData.visitHistoryModel.getDoctorName();
            Paragraph p5 = new Paragraph(docName);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont5= new Font(Font.COURIER,14.0f, Color.GREEN);
            p5.setAlignment(Paragraph.ALIGN_LEFT);
            p5.setFont(paraFont5);

            doc.add(p5);

            String medicalReportTxt="Medical Report";
            Paragraph p6 = new Paragraph(medicalReportTxt);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont6= new Font(Font.COURIER,16.0f, Color.GREEN);
            p6.setAlignment(Paragraph.ALIGN_CENTER);
            p6.setFont(paraFont6);
            p6.getSpacingAfter();

            doc.add(p6);

            String medicalReport=" "+AppData.visitHistoryModel.getMedicationsReport();
            Paragraph p7 = new Paragraph(medicalReport);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont7= new Font(Font.COURIER,14.0f, Color.GREEN);
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont7);

            doc.add(p7);

            String doctorInstructionsTxt=" Doctor's Instructions ";
            Paragraph p8 = new Paragraph(doctorInstructionsTxt);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont8= new Font(Font.COURIER,16.0f, Color.GREEN);
            p8.setAlignment(Paragraph.ALIGN_CENTER);
            p8.setFont(paraFont8);
            p6.getSpacingAfter();
            doc.add(p8);

            String doctorInstructions=" "+AppData.visitHistoryModel.getInstructions();
            Paragraph p9 = new Paragraph(doctorInstructions);
			/* You can also SET FONT and SIZE like this*/
            Font paraFont9= new Font(Font.COURIER,14.0f, Color.GREEN);
            p9.setAlignment(Paragraph.ALIGN_LEFT);
            p9.setFont(paraFont9);

            doc.add(p9);


            //set footer
            Phrase footerText = new Phrase("#ReadyDoctor");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            pdfFooter.setAlignment(HeaderFooter.ALIGN_CENTER);
            pdfFooter.setBorder(Element.HEADER);
            doc.setFooter(pdfFooter);

            Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();

        }
        catch (DocumentException de)
        {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
        catch (IOException e)
        {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

        openPdf(fileName);
    }

    void openPdf(String fileName)
    {
       Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        File file = new File(path,fileName);

        intent.setDataAndType( Uri.fromFile(file), "application/pdf" );
        startActivity(intent);
    }
}
