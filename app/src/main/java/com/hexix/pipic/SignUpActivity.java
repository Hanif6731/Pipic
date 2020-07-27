package com.hexix.pipic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mName;
    private EditText mPSpeed;
    private EditText mPPower;
    private EditText mKSpeed;
    private EditText mKPower;
    private Button mSave, mGetAllKickBoxer;
    private TextView txtGetData;
    private String mAllKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        mAllKickBoxers="";
        mName=findViewById(R.id.txtName);
        mPSpeed=findViewById(R.id.txtPunchSpeed);
        mPPower=findViewById(R.id.txtPunchPower);
        mKSpeed=findViewById(R.id.txtKickSpeed);
        mKPower=findViewById(R.id.txtKickPower);
        mSave=findViewById(R.id.btnSave);
        mGetAllKickBoxer=findViewById(R.id.btnGetAll);
        mGetAllKickBoxer.setOnClickListener(SignUpActivity.this);
        txtGetData=findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(SignUpActivity.this);

        mSave.setOnClickListener(SignUpActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                SaveClicked();
                break;
            case R.id.txtGetData:
                LoadData();
                break;
            case R.id.btnGetAll:
                LoadAllData();
                break;
        }

    }

    public void SaveClicked(){
//        final ParseObject boxer=new ParseObject("Boxer");
//        boxer.put("punch_speed",200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//                    Toast.makeText(SignUpActivity.this,"Boxer instance saved",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        String name=mName.getText().toString();
        int pp=Integer.parseInt(mPPower.getText().toString());
        int ps=Integer.parseInt(mPSpeed.getText().toString());
        int kp=Integer.parseInt(mKPower.getText().toString());
        int ks=Integer.parseInt(mKSpeed.getText().toString());

        try {

            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", name);
            kickBoxer.put("punch_speed", ps);
            kickBoxer.put("punch_power", pp);
            kickBoxer.put("kick_speed", ks);
            kickBoxer.put("kick_power", kp);
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUpActivity.this, kickBoxer.get("name") + " is saved", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    }
                    else{
                        FancyToast.makeText(SignUpActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }
            });
        }
        catch (Exception e){
            FancyToast.makeText(SignUpActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }


    }
    public void LoadData(){
        ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
        parseQuery.getInBackground("nVgxTwBsoJ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(object!=null && e==null){
                    txtGetData.setText(object.get("name")+"\nPunch Power: "+object.get("punch_power")+
                            "\nPunch Speed: "+object.get("punch_speed"));
                }
                else {
                    FancyToast.makeText(SignUpActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }

    public void LoadAllData(){
        ParseQuery<ParseObject> queryGetAll=ParseQuery.getQuery("KickBoxer");
        queryGetAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseObject kickBoxer:objects){
                            mAllKickBoxers+=kickBoxer.getObjectId()+" ";
                        }
                        txtGetData.setText(mAllKickBoxers);
                        //FancyToast.makeText(SignUpActivity.this,"Success full",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    }
                    else {
                        FancyToast.makeText(SignUpActivity.this,"size 0",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }

                }
                else{
                    FancyToast.makeText(SignUpActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }
}