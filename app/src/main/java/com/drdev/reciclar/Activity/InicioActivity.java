package com.drdev.reciclar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drdev.reciclar.Models.Usuario;
import com.drdev.reciclar.R;
import com.drdev.reciclar.Service.DatabaseHelper;
import com.drdev.reciclar.Service.Util;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {

    private EditText inputTextEmail;
    private EditText inputTextSenha;
    private EditText inputTextSenha2;

    private TextView linkLogin;
    private TextView linkLoginPrefix;

    private Button btCadastroLogin;

    private DatabaseHelper _dbHelper;
    private Util _util;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        InstanciarObjetos();
        SetListeners();
    }

    private void InstanciarObjetos(){
        _dbHelper = new DatabaseHelper(InicioActivity.this);
        _util = new Util(InicioActivity.this);

        inputTextEmail = findViewById(R.id.InputTextEmail);
        inputTextSenha = findViewById(R.id.InputTextPassword);
        inputTextSenha2 = findViewById(R.id.InputTextPassword2);
        linkLogin = findViewById(R.id.TextLoginLink);
        linkLoginPrefix = findViewById(R.id.TextLoginLinkPrefix);
        btCadastroLogin = findViewById(R.id.BTCadastroLogin);
    }

    private void SetListeners(){
        btCadastroLogin.setOnClickListener(view -> CheckAction());
        linkLogin.setOnClickListener(view -> MudarViewLogin());
    }

    private void MudarViewLogin() {

        if (!isLogin){
            inputTextSenha2.setVisibility(View.GONE);
            btCadastroLogin.setText("Login");
            linkLogin.setText("Cadastrar");
            linkLoginPrefix.setText("Não possui conta? ");

        }else{
            inputTextSenha2.setVisibility(View.VISIBLE);
            btCadastroLogin.setText("Cadastrar");
            linkLogin.setText("Faça seu login agora");
            linkLoginPrefix.setText("Já possui conta? ");
        }
        isLogin = !isLogin;
    }

    private void CheckAction(){
        if (isLogin)
            LoginUsuario();
        else
            CadastrarUsuario();
    }

    private void CadastrarUsuario(){
        if (ValidarInputs()){
            Usuario model = new Usuario();
            model.Nome = GetNomeUsuario(inputTextEmail.getText().toString());
            model.Email = inputTextEmail.getText().toString();
            model.Senha = inputTextSenha.getText().toString();

            if(_dbHelper.CriarUsuario(model)){
                _util.SaveUserPrefs(getString(R.string.UserNameSharedPrefs),  model.Nome);
                startActivity(new Intent(InicioActivity.this, HomeActivity.class));
            }
            else{
                _util.Toast("Ocorreu um erro ao cadastrar o usuario, tente novamente", Toast.LENGTH_LONG);
            }
        }
    }

    private void LoginUsuario(){
        if (ValidarInputs()){
            Usuario model = new Usuario();
            model.Email = inputTextEmail.getText().toString();
            model.Senha = inputTextSenha.getText().toString();
            Usuario user = _dbHelper.ObterUsuarioLogin(model);

            if (user.Nome != null)
                startActivity(new Intent(InicioActivity.this, HomeActivity.class));
            else
                _util.Toast("Usuario inválido", Toast.LENGTH_LONG);
        }
    }

    private String GetNomeUsuario(String email){
        String[] arr = email.split("@");
        return  arr[0];
    }

    private Boolean ValidarInputs(){
        Boolean result = true;

        if (inputTextEmail.getText().toString().equals("") ){
            _util.Toast("É preciso digitar um email", Toast.LENGTH_LONG);
            result = false;
        }

        if (!inputTextEmail.getText().toString().contains("@") ){
            _util.Toast("Email invalido, tente novamente", Toast.LENGTH_LONG);
            result = false;
        }

        if (inputTextSenha.getText().toString().equals("")){
            _util.Toast("Digite uma senha para prosseguir", Toast.LENGTH_LONG);
            result = false;
        }

        if (!isLogin && inputTextSenha2.getText().toString().equals("")){
            _util.Toast("É preciso confirmar a senha", Toast.LENGTH_LONG);
            result = false;
        }

        if (!isLogin && !inputTextSenha2.getText().toString().equals(inputTextSenha.getText().toString())){
            _util.Toast("As senhas digitadas não são iguais", Toast.LENGTH_LONG);
            result = false;
        }
        return result;
    }
}