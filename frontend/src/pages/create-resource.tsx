import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header";
import MenuLayout from "../components/menu-layout";
import { makePrivateRequest } from "../utils/request";
import TeamForm from "../components/team-form";
import RuleForm from "../components/rule-form";
import MatchForm from "../components/match-form";
import { ResourceProps } from "../utils/type";
import { menuItems, configItems } from "../utils/nav-items";
import uploadTeamImage from "../utils/upload-request";
import SweepstakeForm from "../components/sweepstake-form";
import { previousPath } from "../utils/path-handler";
import { useParticipant } from "../context/ParticipantProvider/useParticipant";

const CreateResource = ( { resource } : ResourceProps) =>
{
    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const participant = useParticipant();

    const url = isSweepstake ? '/boloes' : `/boloes/${sweepstakeId}/${resource}`;
    const navItems = isSweepstake ? menuItems : configItems(sweepstakeId, participant.role, participant.tournament);
    const buttonName = isSweepstake ? "CRIAR" : "ADICIONAR";

    const uploadAndSubmit = (data : any) =>
    {
        uploadTeamImage(data,url+"/upload",onSubmit)
    }

    const onSubmit = (data : any) => 
    {   
        console.log(data)
        makePrivateRequest( {url, data, method: 'POST'} )
            .then((response) =>
            {
                const path = isSweepstake ? location.pathname + '/' + response.data.id : previousPath(location.pathname);
                console.log(path)
                navigate(path)
            })
            .catch((error) => console.log(error))
    }

    return(

        <>
            <Header status="logged" />

            <MenuLayout navItems={navItems}>

            { 
                resource === 'sweepstakes' ? ( <SweepstakeForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                resource === 'regras' ? ( <RuleForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :   
                resource === 'times' ? ( <TeamForm onSubmit={uploadAndSubmit} buttonName={buttonName} create={true} /> ) :
                resource === 'partidas' ? ( <MatchForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default CreateResource