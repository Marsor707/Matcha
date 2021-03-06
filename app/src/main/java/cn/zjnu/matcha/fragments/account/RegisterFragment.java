package cn.zjnu.matcha.fragments.account;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.account.RegisterContract;
import cn.zjnu.matcha.factory.mvp.account.RegisterPresenter;

/**
 * 注册界面的Fragment
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.edit_username)
    EditText mName = null;
    @BindView(R.id.edit_password)
    EditText mPassword = null;
    @BindView(R.id.edit_password2)
    EditText mRePassword = null;
    @BindView(R.id.btn_submit)
    Button mSubmit = null;
    @BindView(R.id.txt_go_login)
    TextView mTxtGoLogin = null;
    @BindView(R.id.edit_sex)
    EditText mEditSex;
    @BindView(R.id.edit_birthday)
    EditText mEditBirthday;

    private IAccountTrigger mAccountTrigger;
    private String[] sexArray = new String[]{"男", "女"};

    @OnClick(R.id.txt_go_login)
    void onGoLoginClick() {
        mAccountTrigger.triggerView();
    }

    @OnClick(R.id.btn_submit)
    void onClickRegister() {
        if (checkForm()) {
            mName.setEnabled(false);
            mPassword.setEnabled(false);
            mRePassword.setEnabled(false);
            mSubmit.setEnabled(false);
            mTxtGoLogin.setEnabled(false);
            mEditSex.setEnabled(false);
            mEditBirthday.setEnabled(false);
            mPresenter.getContext(getContext());

            mPresenter.register(mName.getText().toString(), mPassword.getText().toString());
        } else {
            Matcha.showToast("请检查所填信息是否有误");
        }
    }

    @OnClick(R.id.edit_sex)
    void onSexChooseClick() {
        showSexChooseDialog();
    }

    @OnClick(R.id.edit_birthday)
    void onBirthdayChooserClick() {
        showDataPickerDialog();
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        final String birthday = mEditBirthday.getText().toString();
        final String sex = mEditSex.getText().toString();
        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("用户名不能为空");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (password.isEmpty()) {
            mPassword.setError("密码不能为空");
            isPass = false;
        } else if (password.length() < 4) {
            mPassword.setError("请至少输入4个字符");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            mRePassword.setError("所填信息有误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        if (birthday.isEmpty()) {
            mEditBirthday.setError("出身日期不能为空");
            isPass = false;
        } else {
            mEditBirthday.setError(null);
        }

        if (sex.isEmpty()) {
            mEditSex.setError("性别不能为空");
            isPass = false;
        } else {
            mEditSex.setError(null);
        }
        return isPass;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (IAccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void showSuccess() {
        Matcha.showToast("注册成功");
        mName.setText("");
        mPassword.setText("");
        mRePassword.setText("");
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
        mEditSex.setEnabled(true);
        mEditBirthday.setEnabled(true);
        mAccountTrigger.triggerView();
    }

    @Override
    public void fetchUserNameAndPassword(String username, String password) {
        final String sexStr = mEditSex.getText().toString();
        final int sex = sexStr.equals(sexArray[0]) ? 1 : 0;
        final String birthday = mEditBirthday.getText().toString();
        mPresenter.registerToLocal(username, password, sex, birthday);
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
        mEditSex.setEnabled(true);
        mEditBirthday.setEnabled(true);
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
        mEditSex.setEnabled(true);
        mEditBirthday.setEnabled(true);
    }

    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(sexArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEditSex.setText(sexArray[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDataPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = 2008;
        int month = 8;
        int day = 8;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String birthday = year + "-" + (month + 1) + "-" + dayOfMonth;
                mEditBirthday.setText(birthday);
            }
        }, year, month, day).show();
    }
}
