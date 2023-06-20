import { useParams } from "react-router-dom";
import Header from "../components/header"
import Button from "../components/button";
import MenuLayout from "../components/menu-layout"
import Input from "../components/input";
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { configItems } from "../utils/nav-items"
import { useEffect, useState } from "react";
import { makePrivateRequest } from "../utils/request";
import { Sweepstake } from "../utils/type";


const sweeptakeFormSchema = z.object(
    {
        name: z.string()
               .min(4, 'Deve conter no mínimo 4 caracteres')
               .max(30, 'Deve conter no máximo 30 caracteres'),
        
        private: z.string()
    })

type SweepstakeFormData = z.infer<typeof sweeptakeFormSchema>

const UpdateSweepstake = () =>
{

    const { sweepstakeId } = useParams();
    const { register, handleSubmit, formState : { errors } } = useForm<SweepstakeFormData>({resolver: zodResolver(sweeptakeFormSchema)});
    const [sweepstake, setSweepstake] = useState<Sweepstake>()

    useEffect(() =>
    {
        makePrivateRequest( {url: `/boloes/${sweepstakeId}`} )
            .then((response) =>
            {
                
                setSweepstake(response.data)
            })
            .catch((error) => console.log(error))

    },[sweepstakeId])


    const differentData = (data : any) => sweepstake && (data.name !== sweepstake.name || data.private_ !== sweepstake.private_)

    const onSubmit = (data : SweepstakeFormData) => 
    {  
        const newData  = {name: data.name, private_: data.private === 'true'}
        console.log(newData)
        
        if(differentData(newData))
            makePrivateRequest( {url: `/boloes/${sweepstakeId}`, data: newData, method: 'PUT'} )
                .then((response) =>
                {
                    console.log(response.data)
                    setSweepstake(response.data)
                })
                .catch((error) => console.log(error))
    }

    return(

        <>

            <Header status='logged' />

            <MenuLayout navItems={configItems(sweepstakeId)}>

            {
                sweepstake && 
                (
                    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col h-full w-full justify-between">
                        
                        <div className="flex flex-col gap-8 items-start">

                            <Input<SweepstakeFormData>
                                label="Nome do Bolão"
                                type="text"
                                name="name"
                                register={register}
                                errors={errors}
                                defaultValue={sweepstake.name}
                            />
                            
                        
                            <div className="flex flex-row-reverse gap-4">

                                <label className="flex gap-1 items-center">
                                    <input
                                        className="h-6 w-6"
                                        value="true"
                                        type="radio"
                                        {...register("private")}
                                        name="private"
                                        defaultChecked={sweepstake.private_}
                                    />
                                    Privado
                                </label>

                                <label className="flex gap-1 items-center" >
                                    <input
                                        className="h-6 w-6"
                                        value="false"
                                        type="radio"
                                        {...register("private")}
                                        name="private"
                                        defaultChecked={!sweepstake.private_}
                                    />
                                    Público
                                </label>

                            </div>

                        </div>
                        
                        <div className="mx-auto">
                            <Button> ATUALIZAR BOLÃO </Button>
                        </div>

                    </form> 
                )
            }
            
            </MenuLayout>

        </>
        
    )
}

export default UpdateSweepstake