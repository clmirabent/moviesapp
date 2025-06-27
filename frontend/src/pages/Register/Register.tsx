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
    <div className={styles.container}>
      <div className={styles.image_container}>
        <div className={styles.text_register}>
        </div>
        <img style={{ overflow: "auto" }} src={registerImage} alt="register" />
      </div>

      <div className={styles.form_container}>
        <form style={{ width: "300px" }} onSubmit={handleSubmit(onSubmit)}>
          <div className={styles.input}>
            <label htmlFor="name" style={{ color: "white" }}>User Name</label>
            <br />
            <input
              id="name"
              type="text"
              className={styles.input_text}
              {...register("name", { required: "A user name is required" })}
            />
            {errors.name && (
              <div className={styles.error_message}>{errors.name.message}</div>
            )}
          </div>

          <div className={styles.input}>
            <label htmlFor="email" style={{ color: "white" }}>Email</label>
            <br />
            <input
              id="email"
              className={styles.input_text}
              {...register("email", {
                required: "An email is required",
                pattern: {
                  value: /^\S+@\S+$/i,
                  message: "This email is invalid",
                },
              })}
            />
            {errors.email && (
              <div className={styles.error_message}>{errors.email.message}</div>
            )}
          </div>
          <div className={styles.input}>
            <label htmlFor="password" style={{ color: "white" }}>Password</label>
            <br />
            <input
              id="password"
              type="password"
              className={styles.input_text}
              {...register("password", {
                required: "Password is required",
                pattern: {
                  value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/,
                  message:
                    "Password must have at least 8 characters, one upper case, one lower case and a number",
                },
              })}
            />
            {errors.password && (
              <div className={styles.error_message}>
                {errors.password.message}
              </div>
            )}
          </div>
          <div>
            <button className={styles.form_button} type="submit">
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
