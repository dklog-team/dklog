const getTestById = (id) => {
    const uri = `/test?id=${id}`
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    }
    return request.get(uri, config)
}