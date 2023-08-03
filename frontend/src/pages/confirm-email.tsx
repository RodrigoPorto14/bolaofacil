import { useEffect, useState } from "react"
import { makeRequest } from "../utils/request";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";

const ConfirmEmail = () =>
{
    const { token } = useParams();
    const navigate = useNavigate();

    useEffect(() =>
    {
        const params = { token }
        makeRequest({url : '/users/verify', params})
            .then(response =>
            {
                toast.success(response.data);
                navigate('/login');
            })
            .catch(error =>
            {
                let errorMessage : string = error.response.data.message;

                if(errorMessage.includes("nickname"))
                    errorMessage = "Apelido já existente";
                
                if(errorMessage.includes("email"))
                    errorMessage = "Email já cadastrado";

                toast.error(errorMessage);
                navigate('/login');
            })
    })

    return <></>
}

export default ConfirmEmail;