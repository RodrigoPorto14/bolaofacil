import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header/header";
import MenuLayout from "../components/menu/menu-layout";
import { makePrivateRequest } from "../utils/request";
import TeamForm from "../components/forms/team-form";
import RuleForm from "../components/forms/rule-form";
import MatchForm from "../components/forms/match-form";
import { ResourceProps } from "../utils/type";
import { menuItems, configItems } from "../utils/nav-items";
import uploadTeamImage from "../utils/upload-request";
import SweepstakeForm from "../components/forms/sweepstake-form";
import { previousPath } from "../utils/path-handler";
import { useParticipant } from "../context/ParticipantProvider/useParticipant";
import { toast } from 'react-toastify';

const CreateResource = ( { resource } : ResourceProps) =>
{
    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const participant = useParticipant();

    const url = isSweepstake ? '/boloes' : `/boloes/${sweepstakeId}/${resource}`;
    const navItems = isSweepstake ? menuItems : configItems(sweepstakeId, participant.role, participant.tournament);
    const buttonName = "CRIAR";

    // const uploadAndSubmit = (data : any) =>
    // {
    //     uploadTeamImage(data,url+"/upload",onSubmit)
    // }

    const onSubmit = (data : any) => 
    {   
        makePrivateRequest( {url, data, method: 'POST'} )
            .then((response) =>
            {
                toast.success("Criado com sucesso!");
                let path = previousPath(location.pathname);
                path = isSweepstake ? path + '/' + response.data.id : path;
                navigate(path)
            })
            .catch(error => {})
    }

    return(

        <>
            <Header status="logged" />

            <MenuLayout navItems={navItems}>

            { 
                resource === 'sweepstakes' ? ( <SweepstakeForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                resource === 'regras' ? ( <RuleForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :   
                resource === 'times' ? ( <TeamForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                resource === 'partidas' ? ( <MatchForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default CreateResource