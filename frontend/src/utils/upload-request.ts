import { makeUploadRequest } from "./request"

const uploadTeamImage = (data : any, url : string, onSubmit : any, uri? : string) =>
{
    if(data.imgUri)
    {
        const formData = new FormData();        
        formData.append('file', data.imgUri);
    
        makeUploadRequest( {url, data : formData, method: 'POST'} )
            .then((response) =>
            {
                console.log(response.data)
                onSubmit({name: data.name, imgUri: response.data.uri})
            })
            .catch((error) => console.log(error))
    }
    else 
    {
        data = uri ? {name: data.name, imgUri: uri} : data;
        console.log(data)
        onSubmit(data)
    }
        
    
}

export default uploadTeamImage