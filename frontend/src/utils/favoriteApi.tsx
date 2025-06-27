import api from "./api"

export interface Favorite {
  id: number
  userId: number
  movieId: string
 
}

interface AddFavoriteParams {
  userId: number
  movieId: string
}

interface GetFavoritesParams {
  userId: number
}

interface RemoveFavoriteParams {
  userId: number
  movieId: string
}

export async function addFavorite(
  { userId, movieId }: AddFavoriteParams
): Promise<Favorite | any> {
  try {
    const response = await api.post<Favorite>(`/favorite`, {
      userId,
      movieId,
    })
    return response.data
  } catch (error: any) {
    console.error("Favorite error", error.message)
    return error.response
  }
}


export async function getFavorites(
  { userId }: GetFavoritesParams
): Promise<Favorite[] | any> {
  try {
    const response = await api.get<Favorite[]>(`/users/${userId}/favorite`)
    return response.data
  } catch (error: any) {
    console.error("Error fetching favorites", error.message)
    return error.response
  }
}


export async function removeById({ userId, movieId }: RemoveFavoriteParams): Promise<Favorite | any> {
  try {
    const response = await api.delete<Favorite>(
      `/users/${userId}/favorite/${movieId}`
    )
    return response.data
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
