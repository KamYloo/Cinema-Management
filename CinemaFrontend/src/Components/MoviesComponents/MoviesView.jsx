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

    const {auth,movie} = useSelector(store => store);

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

                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://play-lh.googleusercontent.com/oSTvqJgXHP6C80W2wT5NGzZjh3rCgFLqn3kRJiIrVpCqraflQ1UUyO_gVGvD03l9yAM"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBdl8r-1SmfA7K1nTTm98v_2Kr-rcc6wKUQA&s"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://www.moople.in/blog/wp-content/uploads/2018/02/Brave.jpg"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBdl8r-1SmfA7K1nTTm98v_2Kr-rcc6wKUQA&s"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBdl8r-1SmfA7K1nTTm98v_2Kr-rcc6wKUQA&s"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
                <div className="movie">
                    <i className="play"><FaCirclePlay/></i>
                    <img
                        src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg"
                        alt=""/>
                    <span>Dramat</span>
                    <p>Scary Movie</p>
                </div>
            </div>
        </div>
    )
}

export {MoviesView}