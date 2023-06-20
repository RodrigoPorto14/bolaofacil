import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../components/header";
import MenuLayout from "../components/menu-layout";
import { makePrivateRequest } from "../utils/request";
import TeamForm from "../components/team-form";
import RuleForm from "../components/rule-form";
import MatchForm from "../components/match-form";
import { ResourceProps } from "../utils/type";
import { configItems } from "../utils/nav-items";
import uploadTeamImage from "../utils/upload-request";

const CreateResource = ( { resource } : ResourceProps) =>
{
    const { sweepstakeId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const url = `/boloes/${sweepstakeId}/${resource}`;

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
                const path = location.pathname.replace('/create', '')
                navigate(path)
            })
            .catch((error) => console.log(error))
    }

    const buttonName = "ADICIONAR";

    return(

        <>
            <Header status="logged" />

            <MenuLayout navItems={configItems(sweepstakeId)}>

            { 
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