export const convertShowtime = (dateTimeString) => {
    const dateTime = new Date(dateTimeString);
    const options = { month: 'short', day: '2-digit', year: 'numeric' };
    const formattedDate = dateTime.toLocaleDateString('en-US', options);
    const formattedTime = dateTime.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
    });

    return {
        date: formattedDate,
        time: formattedTime
    };
};