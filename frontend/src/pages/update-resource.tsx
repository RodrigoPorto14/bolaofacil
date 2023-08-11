import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header/header";
import MenuLayout from "../components/menu/menu-layout";
import RuleForm from "../components/forms/rule-form"
import TeamForm from "../components/forms/team-form";
import MatchForm from "../components/forms/match-form";
import { makePrivateRequest } from "../utils/request";
import { ConfigItems } from "../utils/nav-items";
import { useState, useEffect } from "react";
import { previousPath } from "../utils/path-handler";
import { ResourceProps} from "../utils/type";
import uploadTeamImage from "../utils/upload-request";
import SweepstakeForm from "../components/forms/sweepstake-form";
import { toast } from 'react-toastify';

const UpdateResource = ({ resource } : ResourceProps) =>
{
    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId, resourceId } = useParams();
    const [resources, setResources] = useState<any>();
    const navigate = useNavigate();
    const location = useLocation();
    const buttonName = "ATUALIZAR";

    const url = isSweepstake ? `/boloes/${sweepstakeId}` : `/boloes/${sweepstakeId}/${resource}/${resourceId}`;

    useEffect(() =>
    {
        makePrivateRequest( { url } )
            .then((response) =>
            {
                setResources(response.data)
            })
            .catch((error) => console.log(error))

    },[url, resourceId])

    const differentData = (data : any) =>
    {
        for(const key in data)
            if(data[key] !== resources[key])
                return true;
        return false;
    }

    const onDelete = () => 
    {
        makePrivateRequest( {url , method: 'DELETE'} )
            .then((response) =>
            {
                toast.success("Deletado com sucesso!");
                const path = previousPath(location.pathname)
                navigate(path)
            })
            .catch((error) => { toast.error(error.response.data.message); })
    }

    // const uploadAndSubmit = (data : any) =>
    // {
    //     uploadTeamImage(data, url+"upload", onSubmit, (resourceObj as Team).imgUri)
    // }


    const onSubmit = (data : any) => 
    {   
        if(differentData(data))
            makePrivateRequest( {url , data, method: 'PUT'} )
                .then((response) =>
                {
                    toast.success("Atualizado com sucesso!");
                    const path = previousPath(location.pathname)
                    
                    if(!isSweepstake)
                        navigate(path)
                })
                .catch(error => {})
    }

    return(

        <>
            <Header status="logged" />
            <MenuLayout navItems={ConfigItems(sweepstakeId)}>

            { 
                resource === 'sweepstakes' ? ( <SweepstakeForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resources} /> ) : 
                resource === 'regras' ? ( <RuleForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resources} /> ) :   
                resource === 'times' ? ( <TeamForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resources} /> ) :
                resource === 'partidas' ? ( <MatchForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resources} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default UpdateResource