import { useParams, useNavigate, useLocation } from "react-router-dom"
import Header from "../../components/header/header";
import MenuLayout from "../../components/menu/menu-layout";
import { makePrivateRequest } from "../../utils/request";
import TeamForm from "../../components/forms/team-form";
import RuleForm from "../../components/forms/rule-form";
import MatchForm from "../../components/forms/match-form";
import { ResourceProps } from "../../utils/types";
import { menuItems, ConfigItems } from "../../utils/nav-items";
import uploadTeamImage from "../../utils/upload-request";
import SweepstakeForm from "../../components/forms/sweepstake-form";
import { previousPath } from "../../utils/path-handler";
import { toast } from 'react-toastify';
import { _isMatch, _isRule, _isSweepstake, _isTeam } from "../../utils/enums";

const CreateResource = ( { resource } : ResourceProps) =>
{
    const isSweepstake = _isSweepstake(resource);
    const { sweepstakeId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const url = isSweepstake ? '/boloes' : `/boloes/${sweepstakeId}/${resource}`;
    const navItems = isSweepstake ? menuItems : ConfigItems(sweepstakeId);
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
                isSweepstake ? ( <SweepstakeForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                _isRule(resource) ? ( <RuleForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :   
                _isTeam(resource) ? ( <TeamForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                _isMatch(resource) ? ( <MatchForm onSubmit={onSubmit} buttonName={buttonName} create={true} /> ) :
                <></>
            }

            </MenuLayout>
        </>

    )
}

export default CreateResource