import React, { useEffect } from 'react';
import styles from '../Home/Home.module.css';
import popcorn from '../../assets/popcorn.jpg';
import MovieCard from '../../components/MovieCard/MovieCard';
import favoriteApi, { type Favorite } from '../../utils/favoriteApi';
import { useNavigate } from 'react-router-dom';

const Favorites: React.FC = () => {

    const [favorites, setFavorites] = React.useState<Favorite[]>([]);

    const navigate = useNavigate();

    const onGetFavorites = async () => {
        try {
            const response = await favoriteApi.getFavorites();
            if (response.status === 200) {
                setFavorites(response.data);
            } else {
                console.error("Failed to fetch favorites", response.data);
                setFavorites([]);
            }
        } catch (error: any) {
            console.error("Error fetching favorites", error);
            setFavorites([]);
        }
    };

    useEffect(() => {
        onGetFavorites();
    }, []);

    return (
   <div className={styles.root}>
            <div className={styles.container}>
                <img src={popcorn} alt="popcorn" />
                <div className={styles.searchcontainer}>
                    <div className={styles.searchheader}>
                        <div>
                             <button
        className={styles.backButton}
        onClick={() => navigate("/")}
        aria-label="Home"
      >
        ←
      </button>
                            <h1>My Favorites</h1>
                             <ul className={styles.movieList}>
                                {favorites.map((movie) => (
                                    <MovieCard key={movie.movieId} movie={movie.movieDTO} onChageStatus={onGetFavorites}/>
                                ))}                            
                                </ul>
                            </div>
                    </div>
                </div>
            </div>
         </div>
    );
}

export default Favorites;


 