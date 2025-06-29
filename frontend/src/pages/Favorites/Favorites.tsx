import React, { useEffect } from 'react';
import styles from '../Home/Home.module.css';
import filmHomeSide from '../../assets/filmHomeSide.png';
import MovieCard from '../../components/MovieCard/MovieCard';
import favoriteApi, { type Favorite } from '../../utils/favoriteApi';
import { useNavigate } from 'react-router-dom';
import { isAuthenticated } from '../../utils/api';

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
        if (!isAuthenticated())
            navigate("/login");
        else 
            onGetFavorites();
    }, []);

    return (
        <div className={styles.root} style={{ backgroundImage: `url(${filmHomeSide})` }}>
            <button className={styles.backButton} onClick={() => navigate("/")} aria-label="Home"> ←</button>
            <div className={styles.searchcontainer}>
                <div className={styles.searchheader}>
                    <h1 style={{ alignContent: 'center', color: '#011F2B'}}>my favorites.</h1>
                    <div className={styles.movieList}>
                        <ul>
                            {favorites.map((movie) => (
                                <MovieCard key={movie.movieId} movie={movie.movieDTO} onChageStatus={onGetFavorites} />
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Favorites;


