package making_lab.ndhu.hotel_recommendation_system;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Account_Info AC = new Account_Info();

    private Intent go = new Intent();
    private Bundle bd = new Bundle();

    private String HID,Name,Tel,Addr,Url,Text;

    private TextView text_name,text_text;
    private Button btn_url,btn_map,btn_Tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);

        bd = this.getIntent().getExtras();
        HID = bd.getString("HID");
        Name = bd.getString("Name");

        text_name = (TextView) findViewById(R.id.textView_Hotel_Name);
        text_text = (TextView) findViewById(R.id.textView_Hotel_Text);
        btn_url = (Button) findViewById(R.id.button_Url);
        btn_map = (Button) findViewById(R.id.button_Map);
        btn_Tel = (Button) findViewById(R.id.button_Hotel_Tel);

        new MyTask().execute();

        btn_url.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_Tel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Uri uri;
        Intent intent;
        switch (id){
            case R.id.button_Url:
                uri = Uri.parse(Url);
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            case R.id.button_Map:
                uri = Uri.parse("https://www.google.com.tw/maps/place/"+Addr);
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            case R.id.button_Hotel_Tel:
                uri = Uri.parse("tel:+"+Tel);
                intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
                break;
        }
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(AC.url(),AC.user(),AC.passwd());

                Statement st = conn.createStatement();
                String sql = "SELECT * FROM hotel WHERE HID='"+HID+"'";

                final ResultSet rs = st.executeQuery(sql);

                if(rs.next()){
                    Name = rs.getString(2);
                    Tel = rs.getString(4);
                    Url = rs.getString(10);
                    Addr = rs.getString(6);
                    Text = rs.getString(11);
                    conn.close();
                }
                else{
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void Result){
            text_name.setText(Name);
            text_text.setText(Text);
            super.onPostExecute(Result);
        }
    }
}