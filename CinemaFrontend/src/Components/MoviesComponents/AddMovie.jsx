import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom'
import "../../styles/form.css";
import {useDispatch} from "react-redux";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {createMovie} from "../../Redux/Movie/Action.js";
import {format} from "date-fns";

function AddMovie() {
    const [title, setTitle] = useState('')
    const [movieImg, setMovieImg] = useState(null)
    const [description, setDescription] = useState('')
    const [genre, setGenre] = useState('')
    const [duration, setDuration] = useState(0)
    const [dateTime, setDateTime] = useState(null);
    const [showtimes, setShowtimes] = useState([]);
    const [dateList, setDateList] = useState([]);
    const dispatch = useDispatch()
    const navigate = useNavigate();


    const handleAddShowtime = () => {
        if (dateTime) {
            const formattedDate = dateTime.toLocaleDateString();
            const formattedTime = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            setShowtimes([...showtimes, { date: formattedDate, time: formattedTime }]);
            setDateTime(null);  // Reset dateTime field after adding
        }
    };

    const handleAddDateList = () => {
        if (dateTime) {
            const formattedDateTime = format(dateTime, "yyyy-MM-dd'T'HH:mm:ss");
            console.log(formattedDateTime);
            setDateList([...dateList, { time: formattedDateTime }]);
            setDateTime(null);
        }
    };

    const handleRemoveShowtime = (index) => {
        setShowtimes(showtimes.filter((_, i) => i !== index));  // Remove the selected showtime
    };

    const addMovieHandler = (e) => {
        e.preventDefault();
        dispatch(createMovie({title:title,description:description,image:movieImg,genre:genre,duration:duration,showTimes:dateList}))
        setMovieImg('');
        setDuration(0);
        setTitle('');
        setGenre('');
        setShowtimes([]);
        setDateList([]);
        setDescription('');
    }


    return (
        <div className="addMovie">
            <div className="box">
            <div className="title">
                <h2>Add Movie</h2>
            </div>
                <form onSubmit={addMovieHandler}>
                    <div className="editShortText">
                        <h4>(Url) Image</h4>
                        <input type="text" value={movieImg || ''} onChange={(e) => setMovieImg(e.target.value)}></input>
                    </div>
                    <div className="editShortText">
                        <h4>Title</h4>
                        <input type="text" value={title || ''} onChange={(e) => setTitle(e.target.value)}></input>
                    </div>
                    <div className="editLongText">
                        <h4>Description</h4>
                        <textarea value={description || ''} onChange={(e) => setDescription(e.target.value)}></textarea>
                    </div>
                    <div className="editShortText">
                        <h4>Duration</h4>
                        <input type="number" value={duration} onChange={(e) => setDuration(e.target.value)}></input>
                    </div>
                    <div className="editShortText">
                        <h4>Genre</h4>
                        <select value={genre} onChange={(e) => setGenre(e.target.value)}>
                            <option value="Animated">Animated</option>
                            <option value="Documentary">documentary</option>
                            <option value="Horror">Horror</option>
                            <option value="Fiction">Fiction</option>
                            <option value="Comedy">Comedy</option>
                            <option value="Drama">Drama</option>
                            <option value="Action">Action</option>
                            <option value="War">War</option>
                            <option value="Crime">Crime</option>
                            <option value="Historical">Historical</option>
                            <option value="Adventure">Adventure</option>
                        </select>
                    </div>

                    <div className="editShortText">
                        <h4>Showtimes</h4>
                        <div className="chooseDate">
                            <div className="datePicker">
                                <DatePicker
                                    selected={dateTime}
                                    onChange={(selectedDate) => setDateTime(selectedDate)}  // Handle date and time selection
                                    showTimeSelect  // Enable time picker
                                    timeFormat="HH:mm"
                                    timeIntervals={15}
                                    dateFormat="dd/MM/yyyy h:mm aa"
                                    timeCaption="Time"
                                    placeholderText="Select date and time"
                                />
                            </div>

                            <button type="button" onClick={() => {
                                handleAddShowtime()
                                handleAddDateList()
                            }}>
                                Add Showtime
                            </button>
                        </div>
                        <div className="showtimeList">
                            {showtimes.map((showtime, index) => (
                                <div key={index} className="showtimeItem">
                                    <span>{showtime.date} {showtime.time}</span>
                                    <button className="delete" type="button"
                                            onClick={() => handleRemoveShowtime(index)}>
                                        Delete
                                    </button>
                                </div>
                            ))}
                        </div>
                    </div>


                    <div className="buttons">
                        <button onClick={() => navigate("/movies")} className='submit'>Cancel</button>
                        <button type="submit" className='submit'>Send</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export {AddMovie};



