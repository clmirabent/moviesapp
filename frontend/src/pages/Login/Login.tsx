import React from "react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import styles from "../../pages/login/Login.module.css";
import homeStyles from "../Home/Home.module.css";
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
      toast.error(err.message || "Error logging in.");
      setError("email", {
        type: "manual",
        message: err.message || "Invalid credentials.",
      });
    }
  };

  return (
    <div className={homeStyles.root}>
      <div className={styles.overlay}>
        <h1 className={styles.heading}>login.</h1>
        <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
          <div className={styles.inputGroup}>
            <input
              type="email"
              placeholder="type your email here..."
              {...register("email", { required: true })}
              className={styles.input}
            />
            {errors.email && <span className={styles.error}>your email is required.</span>}
          </div>

          <div className={styles.inputGroup}>
            <input
              type="password"
              placeholder="password."
              {...register("password", { required: true })}
              className={styles.input}
            />
            {errors.password && <span className={styles.error}>password is required.</span>}
          </div>

          <button type="submit" className={`${styles.form_button} ${homeStyles.register_button}`}>
            log In
          </button>
        </form>
<p className={styles.registerPrompt}>
  don’t have an account?{' '}
  <Link 
    to="/register" 
    style={{ 
      color: '#6577E0', 
      textDecoration: 'underline', 
      fontFamily: 'SF Pro Display', 
      fontWeight: 300 
    }}
  >
    register
  </Link>
</p>

      </div>
    </div>
  );
}
export default LoginForm;