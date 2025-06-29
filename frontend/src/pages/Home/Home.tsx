import React, { type FC } from 'react';
import styles from './Home.module.css';
import { useForm, type SubmitHandler } from 'react-hook-form';
import { searchMovies, type Movie } from '../../utils/movieApi';
import filmHomeSide from '../../assets/filmHomeSide.png';
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
        <div className={styles.root} style={{ backgroundImage: `url(${filmHomeSide})` }}>
            {isAuthenticated() && <button className={styles.signOut} onClick={() => { clearToken(); navigate("/login"); }} aria-label="Sign out"> sign out</button>}
            {!isAuthenticated() &&
                <div className={styles.searchcontainer}>
                    <h1 style={{ display: 'flex', justifyContent: 'center' }}>your favorites live here.</h1>
                    <div className={styles.navButtons}>
                        <Link to="/login" className={styles.navLink}>
                            <button className={`${styles.form_button} ${styles.login_button}`}>log In</button>
                        </Link>
                        <Link to="/register" className={styles.navLink}>
                            <button className={`${styles.form_button} ${styles.register_button}`}>register</button>
                        </Link>
                    </div>
                </div>}
            {isAuthenticated() &&
                <div className={styles.searchcontainer}>
                    <div className={styles.searchheader}>

                        <h1 style={{ color: '#011F2B' }}>welcome to the movie app.</h1>

                        <form className={styles.searchBar} onSubmit={handleSubmit(onSubmit)}>
                            <input
                                type="text"
                                placeholder="search for a movie."
                                {...register("search", { required: true })}
                            />
                            <button className={`${styles.form_button} ${styles.register_button}`} type="submit">search</button>
                        </form>

                        <span className={styles.error} style={{"visibility": errors.search ? "unset": "hidden"}}>This field is required</span>

                        <button className={`${styles.form_button} ${styles.login_button}`}  >
                            <Link style={{ textDecoration: "none" }} to="/favorites">my favorites</Link>
                        </button>
                    </div>
                    <section className={styles.movieList}>
                        <ul>
                            {movie && (
                                <MovieCard movie={movie} />
                            )}

                        </ul>
                    </section>
                </div>}
        </div>
    );
}

export default Home;