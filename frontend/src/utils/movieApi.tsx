import api from "./api"

export interface Movie {
  imdbID: string
  title: string
  year: string
  poster: string
  isFavorite: boolean
}

export interface SearchParams {
  search: string
}

export async function searchMovies({ search }: SearchParams)
{
  try {
    const response = await api.get<Movie>(`/movies?title=${search}`) 
    return response
  } catch (error: any) {
    console.error("Error searching movies", error.message)
    return null
  }
}

export default {
  searchMovies,
}