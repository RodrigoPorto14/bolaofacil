import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import Input from "./input";
import InputNumber from "./input-number";
import FormLayout from './form-layout';
import { Rule, FormProps } from '../utils/type';



const RuleForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const requiredError = {invalid_type_error: "Campo obrigatório"};

    const numberValidation = z.number(requiredError)
                              .min(0,'Deve ser maior ou igual a 0')
                              .max(99,'Deve ser menor ou igual a 99')

    const ruleFormSchema = z.object(
    {
        name: z.string()
            .nonempty('Campo Obrigatório')
            .min(4, 'Deve conter no mínimo 4 caracteres')
            .max(30, 'Deve conter no máximo 30 caracteres'),
        
        exactScore: numberValidation,
        winnerScore: numberValidation,
        scoreDifference: numberValidation,
        loserScore: numberValidation,
        winner: numberValidation,
    })

    type RuleFormData = z.infer<typeof ruleFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<RuleFormData>({resolver: zodResolver(ruleFormSchema)});

    return(
            <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} >

                <Input<RuleFormData>
                    label="Nome da Regra"
                    type="text"
                    name="name"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.name}
                />

                <InputNumber<RuleFormData>
                    label="Placar Exato"
                    name="exactScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.exactScore}
                />

                <InputNumber<RuleFormData>
                    label="Placar do Vencedor"
                    name="winnerScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.winnerScore}
                />

                <InputNumber<RuleFormData>
                    label="Diferença do Placar"
                    name="scoreDifference"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.scoreDifference}
                />

                <InputNumber<RuleFormData>
                    label="Placar do Perdedor"
                    name="loserScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.loserScore}
                />

                <InputNumber<RuleFormData>
                    label="Vencedor"
                    name="winner"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.winner}
                />                                      
                        
            </FormLayout>
    )
}

export default RuleForm