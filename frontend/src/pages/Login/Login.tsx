import React from "react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
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

  const onSubmit: SubmitHandler<LoginFormInputs> = async (data) => {
    try {
      var token = await loginUser({ email: data.email, password: data.password });
      sessionStorage.setItem("token", token.token);
      toast.success("Signed in successfully!");
      navigate("/");

    } catch (err: any) {
      toast.error(err.message || "Error logging in");
      setError("email", {
        type: "manual",
        message: err.message || "Invalid credentials",
      });
    }
  };

  return (
    <div className={styles.root}>
      <div
        className={styles.background}
        style={{ backgroundImage: `url(${loginImage})` }}
      />

      {/* OVERLAY centrado */}
      <div className={styles.overlay}>
        <h1 className={styles.heading}>Sign In</h1>
        <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
          <div className={styles.inputGroup}>
            <input
              type="email"
              placeholder="Email"
              {...register("email", { required: true })}
              className={styles.input}
            />
            {errors.email && <span className={styles.error}>Email required</span>}
          </div>

          <div className={styles.inputGroup}>
            <input
              type="password"
              placeholder="Password"
              {...register("password", { required: true })}
              className={styles.input}
            />
            {errors.password && <span className={styles.error}>Password required</span>}
          </div>

          <button type="submit" className={styles.submitButton}>
            Sign In
          </button>
        </form>
      </div>
    </div>
  );
}
export default LoginForm;