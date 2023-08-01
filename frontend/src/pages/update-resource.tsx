import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header/header";
import MenuLayout from "../components/menu/menu-layout";
import RuleForm from "../components/forms/rule-form"
import TeamForm from "../components/forms/team-form";
import MatchForm from "../components/forms/match-form";
import { makePrivateRequest } from "../utils/request";
import { configItems } from "../utils/nav-items";
import { useState, useEffect } from "react";
import { previousPath } from "../utils/path-handler";
import { ResourceProps, Resource, Team } from "../utils/type";
import uploadTeamImage from "../utils/upload-request";
import SweepstakeForm from "../components/forms/sweepstake-form";
import { useParticipant } from "../context/ParticipantProvider/useParticipant";
import { toast } from 'react-toastify';

const UpdateResource = ({ resource } : ResourceProps) =>
{
    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId, resourceId } = useParams();
    const [resourceObj, setResourceObj] = useState<Resource>();
    const navigate = useNavigate();
    const location = useLocation();
    const buttonName = "ATUALIZAR";
    const participant = useParticipant();

    const url = isSweepstake ? `/boloes/${sweepstakeId}` : `/boloes/${sweepstakeId}/${resource}/${resourceId}`;

    useEffect(() =>
    {
        console.log(url)
        makePrivateRequest( { url } )
            .then((response) =>
            {
                setResourceObj(response.data)
            })
            .catch((error) => console.log(error))

    },[url, resourceId])

    const onDelete = () => 
    {
        makePrivateRequest( {url , method: 'DELETE'} )
            .then((response) =>
            {
                toast.success("Deletado com sucesso!");
                const path = previousPath(location.pathname)
                navigate(path)
            })
            .catch((error) => 
            {
                console.log(error);
                toast.error("Não foi possível deletar!");
            })
    }

    // const uploadAndSubmit = (data : any) =>
    // {
    //     uploadTeamImage(data, url+"upload", onSubmit, (resourceObj as Team).imgUri)
    // }

    const onSubmit = (data : any) => 
    {   
        console.log(data)
        makePrivateRequest( {url , data, method: 'PUT'} )
            .then((response) =>
            {
                toast.success("Atualizado com sucesso!");
                const path = previousPath(location.pathname)
                
                if(!isSweepstake)
                    navigate(path)
            })
            .catch((error) => console.log(error))
    }

    return(

        <>
            <Header status="logged" />
            <MenuLayout navItems={configItems(sweepstakeId, participant.role, participant.tournament)}>

            { 
                resource === 'sweepstakes' ? ( <SweepstakeForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) : 
                resource === 'regras' ? ( <RuleForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :   
                resource === 'times' ? ( <TeamForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :
                resource === 'partidas' ? ( <MatchForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default UpdateResource