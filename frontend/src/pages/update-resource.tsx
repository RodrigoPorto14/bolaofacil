import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header";
import MenuLayout from "../components/menu-layout";
import RuleForm from "../components/rule-form"
import TeamForm from "../components/team-form";
import MatchForm from "../components/match-form";
import { makePrivateRequest } from "../utils/request";
import { configItems } from "../utils/nav-items";
import { useState, useEffect } from "react";
import { previousPath } from "../utils/path-handler";
import { ResourceProps, Resource, Team } from "../utils/type";
import uploadTeamImage from "../utils/upload-request";

const UpdateResource = ({ resource } : ResourceProps) =>
{
    const { sweepstakeId, resourceId } = useParams();
    const [resourceObj, setResourceObj] = useState<Resource>();
    const navigate = useNavigate();
    const location = useLocation();
    const buttonName = "ATUALIZAR";

    const url = `/boloes/${sweepstakeId}/${resource}/`;

    useEffect(() =>
    {
        makePrivateRequest( {url: url + resourceId} )
            .then((response) =>
            {
                setResourceObj(response.data)
            })
            .catch((error) => console.log(error))

    },[url, resourceId])

    const onDelete = () => 
    {
        makePrivateRequest( {url: url + resourceId, method: 'DELETE'} )
            .then((response) =>
            {
                const path = previousPath(location.pathname)
                navigate(path)
            })
            .catch((error) => console.log(error))
    }


    const uploadAndSubmit = (data : any) =>
    {
        uploadTeamImage(data, url+"upload", onSubmit, (resourceObj as Team).imgUri)
    }

    const onSubmit = (data : any) => 
    {   
        console.log(data)
        makePrivateRequest( {url: url + resourceId, data, method: 'PUT'} )
            .then((response) =>
            {
                console.log(response.data)
                const path = previousPath(location.pathname)
                navigate(path)
            })
            .catch((error) => console.log(error))
    }

    return(

        <>
            <Header status="logged" />

            <MenuLayout navItems={configItems(sweepstakeId)}>

            { 
                resource === 'regras' ? ( <RuleForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :   
                resource === 'times' ? ( <TeamForm onSubmit={uploadAndSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :
                resource === 'partidas' ? ( <MatchForm onSubmit={onSubmit} onDelete={onDelete} buttonName={buttonName} resource={resourceObj} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default UpdateResource