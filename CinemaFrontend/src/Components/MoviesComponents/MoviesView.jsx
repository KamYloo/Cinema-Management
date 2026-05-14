import React, {useEffect, useState} from 'react'
import "../../styles/MovieView.css"
import { BiSearchAlt } from "react-icons/bi";
import { FaCirclePlay } from "react-icons/fa6";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getAllMoviesAction} from "../../Redux/MovieService/Action.js";

function MoviesView() {
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const auth = useSelector(store => store.auth);
    const movie = useSelector(store => store.movie);

    const filteredMovies = movie.movies.filter(movieItem =>
        movieItem.title.toLowerCase().includes(searchQuery.toLowerCase())
    );

    useEffect(() => {
        dispatch(getAllMoviesAction())
    }, [dispatch])

    return (
        <div className='moviesView'>
            <div className="searchBox">
                <div className="search">
                    <input type="text" placeholder='Search Movie...'
                           value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)}/>
                    <i className='searchIcon'><BiSearchAlt /></i>
                </div>
                <button>Search</button>
                {auth.reqUser?.admin && <button onClick={() => navigate("/movies/add")}>Add Movie</button>}
            </div>
            <div  className="movies">
                {filteredMovies.map((item) => (
                    <div className="movie" key={item.id} onClick={() => navigate(`movie/${item.id}`)}>
                        <i className="play"><FaCirclePlay/></i>
                        <img
                            src={item?.image || ''}
                            alt=""/>
                        <span>{item?.genre}</span>
                        <p>{item?.title}</p>
                    </div>
                ))}
            </div>
        </div>
    )
}

export {MoviesView}