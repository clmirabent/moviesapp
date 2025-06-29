import React, { type FC } from "react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import styles from "./Register.module.css";
import loginStyle from "../Login/Login.module.css";
import homeStyles from "../Home/Home.module.css";

import registerImage from "../../assets/registerImage.jpg";
import { registerUser } from "../../utils/userApi";

interface RegisterFormInputs {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

const Register: FC = () => {
  const {register,handleSubmit,formState: { errors },} = useForm<RegisterFormInputs>();

  const navigate = useNavigate();

  const onSubmit: SubmitHandler<RegisterFormInputs> = async (data) => {
    try {
      var auth = await registerUser({userName: data.name, email: data.email, password: data.password});
      sessionStorage.setItem("token", auth.token);
      navigate("/"); 
    } catch (err: any) {
      console.error("Error to register", err);
      alert(err.message || "Error to register");
    }
  };

  return (
   <div className={homeStyles.root}>
      <div className={loginStyle.overlay}>
        <h1 className={loginStyle.heading} style={{ color: '#011F2B' }}>register.</h1>
        <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
          <div className={loginStyle.inputGroup}>
            <input
              type="text"
              placeholder="type your username here."
              {...register("name", { required: "a user name is required." })}
              className={loginStyle.input}
            />
            {errors.name && (
              <span className={styles.error}>{errors.name.message}</span>
            )}
          </div>
          <div className={loginStyle.inputGroup}>
            <input
              type="email"
              placeholder="type your email here."
              {...register("email", {
                required: "an email is required.",
                pattern: {
                  value: /^\S+@\S+$/i,
                  message: "this email is invalid",
                },
              })}
              className={loginStyle.input}
            />
            {errors.email && (
              <span className={styles.error}>{errors.email.message}</span>
            )}
          </div>
          <div className={loginStyle.inputGroup}>
            <input
              type="password"
              placeholder="password."
              {...register("password", {
                required: "password is required.",
                pattern: {
                  value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/,
                  message:
                    "min 8 chars, 1 uppercase, 1 lowercase & 1 number",
                },
              })}
              className={loginStyle.input}
            />
            {errors.password && (
              <span className={styles.error}>{errors.password.message}</span>
            )}
          </div>

          <button type="submit" className={`${loginStyle.form_button} ${homeStyles.register_button}`}>
            register
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
