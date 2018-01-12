package making_lab.ndhu.hotel_recommendation_system;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Account_Info AC = new Account_Info();
    private EditText edit_ID,edit_Passwd;
    private Button btn_SignIn,btn_SignUp;
    private TextView text;
    private Intent go = new Intent();
    private Bundle bd = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        edit_ID = (EditText) findViewById(R.id.editText_ID);
        edit_Passwd = (EditText) findViewById(R.id.EditText_Passwd);

        text = (TextView) findViewById(R.id.textView);

        btn_SignIn = (Button) findViewById(R.id.button_SignIn);
        btn_SignUp = (Button) findViewById(R.id.button_SignUp);

        btn_SignIn.setOnClickListener(this);
        btn_SignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id){
            case R.id.button_SignIn:
                new MyTask().execute();
                break;
            case R.id.button_SignUp:
                go.setClass(AccountActivity.this,SignUpActivity.class);
                startActivity(go);
                break;
        }
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        int i = 0;
        String s_id,s_passwd,s_uid,s_name;
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(AC.url(),AC.user(),AC.passwd());

                Statement st = conn.createStatement();
                String sql = "SELECT * FROM user WHERE ID='"+edit_ID.getText().toString()+"'";

                final ResultSet rs = st.executeQuery(sql);

                if(rs.next()){
                    s_uid = rs.getString(1);
                    s_id = rs.getString(2);
                    s_passwd = rs.getString(3);
                    s_name = rs.getString(4);
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
            if(edit_ID.getText().toString().equals(s_id)){
                if(edit_Passwd.getText().toString().equals(s_passwd)){
                    go.setClass(AccountActivity.this,MainActivity.class);
                    bd.putString("UID",s_uid);
                    bd.putString("Name",s_name);
                    go.putExtras(bd);
                    startActivity(go);
                    AccountActivity.this.finish();
                }
                else{
                    Toast.makeText(AccountActivity.this,"帳號或密碼錯誤",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(AccountActivity.this,"帳號或密碼錯誤",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(Result);
        }
    }
}