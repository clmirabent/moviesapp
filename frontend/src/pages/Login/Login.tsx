import React from "react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";

import styles from "../../pages/login/Login.module.css";
import loginImage from "../../assets/loginImage.jpg";
import { loginUser } from "../../utils/userApi";

interface LoginFormInputs {
  email: string;
  password: string;
}

const LoginForm: React.FC = () => {

  const {register,handleSubmit,setError,formState: { errors },} = useForm<LoginFormInputs>();

  const navigate = useNavigate();


//when the user submits the form, we call the onLogin function from UserContext
  const onSubmit: SubmitHandler<LoginFormInputs> = async (data) => {
    try {
      var token = await loginUser({ email: data.email, password: data.password });
      sessionStorage.setItem("token", token.token);
      navigate("/"); 

    } catch (err: any) {
      
      setError("email", {
        type: "manual",
        message: err.message || "Error to login",
      });
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.image_container}>
        <div className={styles.text_login}>
        </div>
        <img className={styles.img} src={loginImage} alt="login imagen" />
      </div>

      <div className={styles.form_container}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={styles.input}>
            <label htmlFor="email" style={{ color: "white" }}>Email</label>
            <input
              id="email"
              {...register("email", { required: "An email is required" })}
            />
            {errors.email && (
              <p className={styles.error}>{errors.email.message}</p>
            )}
          </div>

          <div className={styles.input}>
            <label htmlFor="password" style={{ color: "white" }}>Password</label>
            <input
              id="password"
              type="password"
              {...register("password", {
                required: "A password is required",
              })}
            />
            {errors.password && (
              <p className={styles.error}>{errors.password.message}</p>
            )}
          </div>

          <button className={styles.form_button} type="submit">
            <span>Sign In</span>
          </button>

          <Link to="/register">
            <div className={styles.register}>
              <button
                type="button"
                className={styles.register_button}
              >
                <span>Are you new here? Create an account</span>
              </button>
            </div>
          </Link>
        </form>
      </div>
    </div>)}

export default LoginForm;