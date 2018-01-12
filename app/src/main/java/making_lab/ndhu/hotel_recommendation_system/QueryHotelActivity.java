package making_lab.ndhu.hotel_recommendation_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QueryHotelActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Intent go = new Intent();
    private Bundle bd = new Bundle();

    private String area,lv,flag;

    private Spinner sp_area;
    private RadioGroup bg_lv;
    private Button btn_sumit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_hotel);

        bd = this.getIntent().getExtras();
        flag = bd.getString("flag");

        sp_area = (Spinner) findViewById(R.id.spinner_Area);
        bg_lv = (RadioGroup) findViewById(R.id.RadioGroup_LV);
        btn_sumit = (Button) findViewById(R.id.button_submit);

        ArrayAdapter<CharSequence> adapter_area = ArrayAdapter.createFromResource(this,R.array.area_array,android.R.layout.simple_spinner_item);
        adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_area.setAdapter(adapter_area);
        sp_area.setOnItemSelectedListener(this);

        btn_sumit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int selectid = bg_lv.getCheckedRadioButtonId();
        switch (id){
            case R.id.button_submit:
                switch (selectid){
                    case R.id.radioButton1:
                        lv = "1";
                        break;
                    case R.id.radioButton2:
                        lv = "2";
                        break;
                    case R.id.radioButton3:
                        lv = "3";
                        break;
                }
                go.setClass(QueryHotelActivity.this,ListHotelActivity.class);
                if(flag.equals(3)){
                    bd.putString("flag","31");
                }
                else{

                    bd.putString("flag","1");
                }
                bd.putString("area",area);
                bd.putString("lv",lv);
                go.putExtras(bd);
                startActivity(go);
                QueryHotelActivity.this.finish();
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        area = String.valueOf(adapterView.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}