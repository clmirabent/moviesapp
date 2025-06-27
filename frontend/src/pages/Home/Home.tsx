import React, { type FC } from 'react';
import styles from './Home.module.css';
import { useForm, type SubmitHandler } from 'react-hook-form';
import { searchMovies, type Movie } from '../../utils/movieApi';
import popcorn from '../../assets/popcorn.jpg';
import MovieCard from "../../components/MovieCard/MovieCard";
import { toast } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import { clearToken, isAuthenticated } from '../../utils/api';


type SearchInput = {
    search: string;
};

const Home: FC = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<SearchInput>();
    const [movie, setMovie] = React.useState<Movie | null>(null);

    const navigate = useNavigate();

    // Function to handle the search submission
    const onSubmit: SubmitHandler<SearchInput> = async (data) => {
        if (!sessionStorage.getItem("token")) {
            toast.warning("Please, sign in to search a movie");
            setTimeout(() => navigate("/login"), 200);
            return;
        }
        try {
            var response = await searchMovies({ search: data.search });
            if (response?.status == 404) {
                alert("Movie not found");
            }
            if (response?.status == 200) {
                setMovie(response.data);
            }

        } catch (err: any) {
            console.error("Error during search", err);
            alert(err.message || "Error during search");
        }
    };

    return (
        <div className={styles.root} style={{ backgroundImage: `url(${popcorn})` }}>
            {isAuthenticated() && <button className={styles.signOut} onClick={() => {clearToken(); navigate("/login");}} aria-label="Sign out"> Sign out</button>}
            {!isAuthenticated() &&
                <div className={styles.searchcontainer}>
                    <div className={styles.navButtons}>
                        <Link to="/login" className={styles.navLink}>
                            <button className={styles.form_button}>Sign In</button>
                        </Link>
                        <Link to="/register" className={styles.navLink}>
                            <button className={`${styles.form_button} ${styles.register_button}`}>Sign Up</button>
                        </Link>
                    </div>
                </div>}
            {isAuthenticated() &&
                <div className={styles.searchcontainer}>
                    <div className={styles.searchheader}>
                        <div>
                            <h1>Welcome to the Movie App</h1>

                            <form onSubmit={handleSubmit(onSubmit)}>
                                <div className={styles.input}>
                                    <input
                                        type="text"
                                        placeholder="Search for a movie..."
                                        {...register("search", { required: true })}
                                    />
                                    {errors.search && <span className={styles.error}>This field is required</span>}
                                </div>
                                <button className={styles.form_button} type="submit">Search</button>
                            </form>
                            <button className={styles.form_button}  >
                                <Link style={{ textDecoration: "none" }} to="/favorites">My Favorites</Link>
                            </button>
                            <section className={styles.movieList}>
                                <ul>
                                    {movie && (
                                        <MovieCard movie={movie} />
                                    )}

                                </ul>
                            </section>
                        </div>
                    </div>
                </div>}
        </div>
    );
}

export default Home;