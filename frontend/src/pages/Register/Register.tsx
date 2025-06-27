import React, { type FC } from "react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import styles from "./Register.module.css";
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
    <div className={styles.root}>
      <div
        className={styles.background}
        style={{ backgroundImage: `url(${registerImage})` }}
      />

      <div className={styles.overlay}>
        <h1 className={styles.heading}>Sign Up</h1>
        <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
          <div className={styles.inputGroup}>
            <input
              type="text"
              placeholder="User Name"
              {...register("name", { required: "A user name is required" })}
              className={styles.input}
            />
            {errors.name && (
              <span className={styles.error}>{errors.name.message}</span>
            )}
          </div>
          <div className={styles.inputGroup}>
            <input
              type="email"
              placeholder="Email"
              {...register("email", {
                required: "An email is required",
                pattern: {
                  value: /^\S+@\S+$/i,
                  message: "This email is invalid",
                },
              })}
              className={styles.input}
            />
            {errors.email && (
              <span className={styles.error}>{errors.email.message}</span>
            )}
          </div>
          <div className={styles.inputGroup}>
            <input
              type="password"
              placeholder="Password"
              {...register("password", {
                required: "Password is required",
                pattern: {
                  value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/,
                  message:
                    "Min 8 chars, 1 uppercase, 1 lowercase & 1 number",
                },
              })}
              className={styles.input}
            />
            {errors.password && (
              <span className={styles.error}>{errors.password.message}</span>
            )}
          </div>

          <button type="submit" className={styles.submitButton}>
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
