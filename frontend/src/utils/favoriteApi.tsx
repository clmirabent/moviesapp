import api from "./api"
import type { Movie } from "./movieApi"

export interface Favorite {
  id: number
  movieId: string
  movieDTO: Movie
}

interface AddFavoriteParams {
  movieId: string
}

interface RemoveFavoriteParams {
  movieId: string
}

export async function addFavorite(
  { movieId }: AddFavoriteParams
): Promise<Favorite | any> {
  try {
    const response = await api.post<Favorite>(`/favorites`, {
      movieId,
    })
    return response
  } catch (error: any) {
    console.error("Favorite error", error.message)
    return error.response
  }
}


export async function getFavorites(): Promise<Favorite[] | any> {
  try {
    const response = await api.get<Favorite[]>(`/favorites`)
    return response
  } catch (error: any) {
    console.error("Error fetching favorites", error.message)
    return error.response
  }
}

export async function removeById({ movieId }: RemoveFavoriteParams): Promise<Favorite | any> {
  try {
    const response = await api.delete<Favorite>(
      `/favorites/${movieId}`
    )
    return response
  } catch (error: any) {
    console.error("Error removing favorites", error.message)
    return error.response
  }
}

export default {
  addFavorite,
  getFavorites,
  removeById,
}
