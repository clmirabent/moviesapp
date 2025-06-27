import React, { type FC } from 'react';
import styles from './Home.module.css';
import { useForm, type SubmitHandler } from 'react-hook-form';
import { searchMovies, type Movie } from '../../utils/movieApi';

type SearchInput = {
    search: string;
};

const Home: FC = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<SearchInput>();

    const [movie, setMovie] = React.useState<Movie | null>(null);
    
    const onSubmit: SubmitHandler<SearchInput> = async (data) => {
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
         <div className={styles.root}>
             <div className={styles.container}> 
            <div className={styles.background}>
                <img src="https://unsplash.com/es/fotos/palomitas-de-maiz-en-un-tazon-de-vidrio-transparente-ny-lHmsHYHk" alt="Background" />
            </div>
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
        </div>

        <div className={styles.movieList}>
            <ul>
                {movie &&
                    <li key={movie.imdbID}>
                        <h3>{movie.title} ({movie.year})</h3>
                        <img src={movie.poster} alt={movie.title} />   
                    </li>
                }
            </ul>        
        </div>
            </div>
            </div>
        </div>
    </div>
    );
};

export default Home;