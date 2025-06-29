import React, { useEffect, useState } from "react";
import UseAnimations from "react-useanimations";
import heart from "react-useanimations/lib/heart";
import styles from "./MovieCard.module.css";
import defaultPoster from "../../assets/defaultposter.jpg";
import { type Movie } from "../../utils/movieApi";
import favoriteApi from "../../utils/favoriteApi";

type Props = {
    movie: Movie;
    onChageStatus?: () => void;
};

const MovieCard: React.FC<Props> = ({ movie, onChageStatus }) => {
    const [isFavorite, toggleFavorite] = useState(movie.isFavorite);

    useEffect(() => {
        toggleFavorite(movie.isFavorite);
    }, [movie]);

    const onChangeFavorite =  async(isFavorite: boolean) => { 
        if (isFavorite) {
            try {
                const response = await favoriteApi.addFavorite({movieId: movie.imdbID});
                if (response.status === 200) {
                    toggleFavorite(true);
                    onChageStatus && onChageStatus();
                } else {
                    console.error("Failed to add favorite", response.data);
                }
            } catch (error: any) {
                console.error("Error adding favorite", error);
            }
        } else {
            try {
                const response = await favoriteApi.removeById({movieId: movie.imdbID});
                if (response.status === 204) {
                    toggleFavorite(false);
                    onChageStatus && onChageStatus();
                } else {
                    console.error("Failed to remove favorite", response.data);
                }
            } catch (error: any) {
                console.error("Error removing favorite", error);
            }
        }
    };

    return (
        <li className={styles.movieCard}>
            <div
                className={styles.posterContainer}
                >
                <div
                    className={styles.poster}
                    style={{ backgroundImage: `url(${movie.poster !== "N/A" ? movie.poster : defaultPoster})` }}

                />
            </div>
            <div className={styles.info}>
                <div className={styles.infoText}>
                    <h3 className={styles.title}>{movie.title}</h3>
                    <p className={styles.year}>{movie.year}</p>
                </div>
            <div className={styles.heartButton}>
                <UseAnimations
                    animation={heart}
                    strokeColor={isFavorite ? "#77919B" : "#fff"}
                    fillColor={isFavorite ? "#77919B" : "transparent"}
                    reverse={!isFavorite}
                    onClick={() => onChangeFavorite(!isFavorite)}
                    size={32}
                    style={{ cursor: "pointer" }}
                    />

            </div>
            </div>
        </li>
    );
};

export default MovieCard;
