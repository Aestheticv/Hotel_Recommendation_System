package making_lab.ndhu.hotel_recommendation_system;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ListHotelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;

    private String [] HID;
    private String [] Name;
    private String [] Addr;

    private Intent go = new Intent();
    private Bundle bd = new Bundle();
    private Account_Info AC = new Account_Info();

    private String flag;
    private String area,lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hotel);

        bd = this.getIntent().getExtras();
        flag = bd.getString("flag");

        switch (flag){
            case "0":
                break;
            case "1":
                area = bd.getString("area");
                lv = bd.getString("lv");
                break;
        }

        new MyTask().execute();

        list = (ListView) findViewById(R.id.list_data);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        go.setClass(ListHotelActivity.this,HotelInfoActivity.class);
        bd.putString("HID",HID[i]);
        go.putExtras(bd);
        startActivity(go);
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        private int i = 0;
        private String sql;
        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(AC.url(),AC.user(),AC.passwd());

                Statement st = conn.createStatement();
                switch (flag){
                    case "0":
                        sql = "SELECT * FROM hotel";
                        break;
                    case "1":
                        switch (lv){
                            case "1":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND Class = '1'";
                                break;
                            case "2":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND (Class = '2' OR Class = '3')";
                                break;
                            case "3":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND Class = '4'";
                        }
                        break;
                    case "3":
                        switch (lv){
                            case "1":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND Class = '1' ORDER BY Grade DESC";
                                break;
                            case "2":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND (Class = '2' OR Class = '3') ORDER BY Grade DESC";
                                break;
                            case "3":
                                sql = "SELECT * FROM hotel WHERE ZIP = '"+area+"' AND Class = '4'  ORDER BY Grade DESC";
                        }
                        break;
                }


                final ResultSet rs = st.executeQuery(sql);

                //Get data size
                if(rs.next()){
                    while(rs.next()){
                        i++;
                    }
                    //Set Array size
                    HID = new String[i];
                    Name = new String[i];
                    Addr = new String[i];
                    i=0;

                    //Move to first data
                    rs.first();

                    //Store data to array
                    do{
                        HID[i] = rs.getString(1);
                        Name[i] = rs.getString(2);
                        Addr[i] = rs.getString(6);
                        i++;
                    }while(rs.next());
                }
                else{
                    HID = new String[0];
                    Name = new String[0];
                    Addr = new String[0];
                }


                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void Result){
            MyAdapter adapter = new MyAdapter(ListHotelActivity.this);
            list.setAdapter(adapter);
            super.onPostExecute(Result);
        }
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        public MyAdapter(Context c){
            inflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return Name.length;
        }

        @Override
        public Object getItem(int i) {
            return Name[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.layout_list_hotel_adapter,null);
            TextView aName,aAddress;

            aName = (TextView) view.findViewById(R.id.text_name);
            aAddress = (TextView) view.findViewById(R.id.text_addr);

            aName.setText(Name[i]);
            aAddress.setText(Addr[i]);
            return view;
        }
    }
}
