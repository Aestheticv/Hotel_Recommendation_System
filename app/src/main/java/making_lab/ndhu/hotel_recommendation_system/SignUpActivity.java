package making_lab.ndhu.hotel_recommendation_system;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_ID,edit_Passwd,edit_Name,edit_Age,edit_Income;
    private String ID,Passwd,Name,Age,Income,sql_id;
    private Button btn_Submit;
    private Account_Info AC = new Account_Info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("註冊");

        edit_ID = (EditText) findViewById(R.id.edit_ID);
        edit_Passwd = (EditText) findViewById(R.id.edit_Passwd);
        edit_Name = (EditText) findViewById(R.id.edit_Name);
        edit_Age = (EditText) findViewById(R.id.edit_Age);
        edit_Income = (EditText) findViewById(R.id.edit_Income);

        btn_Submit = (Button) findViewById(R.id.btn_submit);
        btn_Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_submit:
                ID = edit_ID.getText().toString();
                Passwd = edit_Passwd.getText().toString();
                Name = edit_Name.getText().toString();
                Age = edit_Age.getText().toString();
                Income = edit_Income.getText().toString();

                if(ID.equals("")||Passwd.equals("")||Name.equals("")||Age.equals("")||Income.equals("")){
                    Toast.makeText(SignUpActivity.this,"欄位不能為空",Toast.LENGTH_SHORT).show();
                }
                else{
                    new MyTask().execute();
                }

                break;
        }
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        private int flag = -1;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(AC.url(),AC.user(),AC.passwd());

                Statement st = conn.createStatement();
                String sql = "SELECT ID FROM user WHERE ID='"+ID+"'";
                ResultSet rs = st.executeQuery(sql);

                if(rs.next()){
                    flag = 0;
                    conn.close();
                }
                else{
                    flag = 1;
                    String sql2 = "INSERT INTO user (ID,Passwd,Name,Age,Income) " +
                            "VALUES ('"+ID+"',"+"'"+Passwd+"',"+"'"+Name+"',"+"'"+Age+"',"+"'"+Income+"')";
                    st.executeUpdate(sql2);
                    conn.close();
                }

            }catch (Exception e){

                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void Result){
            switch (flag){
                case 0:
                    Toast.makeText(SignUpActivity.this,"帳號已存在"+sql_id,Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(SignUpActivity.this,"註冊成功",Toast.LENGTH_SHORT).show();
                    SignUpActivity.this.finish();
                    break;
            }
            super.onPostExecute(Result);
        }
    }
}