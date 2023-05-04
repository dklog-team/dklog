const displayToggle = (target) => {
    if (target.style.display === 'none') {
        target.style.display = 'block';
        return;
    }
    target.style.display = 'none';
}