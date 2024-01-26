import Header from '../components/header/header'
import Footer from '../components/footer/footer'
import MainButton from '../components/buttons/button-main'

const bolaoFacil = require('../img/bolao.png') as string;

const h2Class = "text-left text-sm font-bold";
const paragraphSize = "text-sm";


const Home = () =>
{
    return(

        <>

            <Header  status='not-logged' />

            <main className="flex flex-grow items-center p-8 gap-4 flex-col-reverse lg:flex-row lg:justify-evenly ">

                <div className='flex flex-col max-w-[600px] gap-4 items-center'>
                    <h1 className="text-xl font-bold"> 🚀 Bem-vindo ao BolãoFácil - O Seu Mundo de Palpites Online! 🏆 </h1>

                    <p className={paragraphSize}>
                        Descubra a emoção de competir e palpitar como nunca antes em nosso universo de bolões online! No BolãoFácil, você tem o poder de criar bolões personalizados ou participar de torneios emocionantes, seja no campo dos esportes tradicionais ou no cenário eletrizante dos e-sports.
                    </p>

                    <p className={h2Class}> ✨ Crie Seu Próprio Império de Bolões: </p>
                    <p className={paragraphSize}>
                        Seja o arquiteto do seu próprio destino esportivo! Com nossa plataforma intuitiva, você pode criar bolões exclusivos em poucos cliques. Personalize as regras, defina as partidas, e convide seus amigos para embarcarem nessa jornada competitiva ao seu lado.
                    </p>

                    <h2 className={h2Class}> ⚽🎮 Diversão em Todos os Campos: </h2>
                    <p className={paragraphSize}>
                        Do gramado ao teclado, suamos todos os detalhes para oferecer uma experiência única em bolões. Palpite nos jogos de futebol mais aguardados, mergulhe no universo dos e-sports e desafie seus amigos em torneios de tirar o fôlego. Aqui, a paixão pelo esporte é universal!
                    </p>

                    <h2 className={h2Class}> 📈 Acompanhe o Pódio: </h2>
                    <p className={paragraphSize}>
                        O BolãoFácil não é apenas sobre palpites, é sobre liderança. Acompanhe em tempo real o ranking, veja como suas previsões se comparam com as dos seus amigos e prove que você é o verdadeiro mestre dos palpites. A disputa pela coroa nunca foi tão emocionante!
                    </p>

                    <h2 className={h2Class}> 🆓 Totalmente Gratuito, Sem Letras Miúdas: </h2>
                    <p className={paragraphSize}>
                        Aqui, a diversão é para todos, e o melhor de tudo, é totalmente gratuito! Sem taxas escondidas, sem surpresas desagradáveis. O BolãoFácil é o seu passaporte para a emoção dos palpites, sem nenhum custo adicional.
                    </p>

                    <MainButton to="/login" >CRIAR BOLÃO </MainButton>
                </div>

                <img className='w-[350px] h-[350px] sm:w-[600px] sm:h-[600px]' src={bolaoFacil} alt="Imagem Bolão Fácil" /> 

            </main>

            {/* <Footer /> */}

        </>
            
    )
}

export default Home