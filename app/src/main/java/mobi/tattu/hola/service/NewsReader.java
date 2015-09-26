package mobi.tattu.hola.service;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import mobi.tattu.hola.data.DataStore;
import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.model.News;
import mobi.tattu.utils.Tattu;

/**
 * Created by cristian on 26/09/15.
 */
public class NewsReader {
    private static NewsReader ourInstance = new NewsReader();
    private ArrayList<News> mNewsArrayList;
    private TextToSpeech mTextToSpeech;
    private Context mContext;
    private Locale mLocale = new Locale("es", "AR");
    public static News newsNotification;

    public static NewsReader getInstance() {
        return ourInstance;
    }

    private NewsReader() {
    }

    public void start(Context context) {
        this.mContext = context;
        this.mNewsArrayList = new ArrayList<>();

        this.mNewsArrayList.add(createNews("La Presidenta llega hoy a Nueva York para hablar en la ONU",
                "Será su último viaje como mandataria; incógnita por el abordaje del tema AMIA,Visitará cuatro veces más la provincia para mejorar el magro resultado de las PASO",
                "NUEVA YORK.- Adelantó un día su llegada y apenas hable ante la Asamblea General de las Naciones Unidas, pasado mañana, dejará esta ciudad, a la que le dedicó asistencia perfecta en los últimos 12 años.<br><br>Cristina Kirchner emprende desde hoy su último viaje a los Estados Unidos con un tema pendiente: el fallido acuerdo con Irán. Se descuenta que hablará de la reciente resolución de la ONU que aprobó los nueve principios promovidos por su gobierno para acotar el accionar de los fondos buitre en la renegociación de las deudas soberanas. Pero la incógnita en la previa a su arribo pasaba por el enfoque discursivo que le dará a la estancada causa AMIA.<br><br>Esta vez, su discurso en la ONU la encuentra con la irresuelta muerte del fiscal que investigaba el atentado, Alberto Nisman. Hasta aquí llegaron ayer familiares de las víctimas, encabezados por Sergio Burstein y alineados al oficialismo, para sumarse a la delegación oficial. Aunque la incógnita es hasta dónde llevará el tema Cristina Kirchner, la presencia de los familiares delata que la Presidenta reinstalará el debate, sobre todo ante la vinculación entre los fondos buitre y Nisman que ella había denunciado en abril pasado, después de que así lo revelara Jorge Elbaum, ex director ejecutivo de la DAIA.<br><br>No es casual que junto con la Presidenta partiera anoche desde Buenos Aires el jefe de la Agencia Federal de Inteligencia (AFI), Oscar Parrilli, quien acusó recientemente al gobierno de los Estados Unidos de proteger a Antonio Jaime Stiuso, el ex expía que tras declarar ante la fiscal Viviana Fein, que investiga la muerte de Nisman, se supone que recaló en Miami. Hace un mes, Parrilli exigió al gobierno de Barack Obama que dijera <br><br>qué está haciendo Stiuso [en Estados Unidos], en carácter de qué se encuentra y cuál es el domicilio que tiene.<br><br>Cristina Kirchner podría retomar el reclamo en la sesión del lunes cuando, a las 16, hable por última vez en uno de los escenarios que siempre la deslumbró. Primero junto a Néstor Kirchner, y después durante sus ocho años, jamás faltó a esta cita.\nLa Presidenta pasará el fin de semana en el hotel Mandarin Oriental, frente a Columbus Circle. Cuando llegue, la zona ya habrá vuelto a la normalidad después de que ayer se vistiera de blanco y amarillo con los cientos de neoyorquinos que se acercaron para ver al papa Francisco, antes de que deje esta ciudad rumbo a Filadelfia. Al menos por la agenda que manejaban en la comitiva argentina, no había espacio para que volvieran a cruzarse\nComo muestra de un gobierno que se termina, entre las actividades presidenciales solo figura el discurso del lunes en la ONU. Cristina fue invitada para hablar mañana en un encuentro de la Secretaría de la Mujer de las Naciones Unidas, pero hasta ayer no había confirmado su asistencia y canceló una reunión con empresarios que se había pautado para el martes. El lunes tiene previsto volverse.\nEn la comitiva que partió anoche viajaron también el canciller Héctor Timerman y el secretario legal y técnico, Carlos Zannini, el compañero de fórmula de Daniel Scioli. Se espera que el lunes llegue, en avión de línea, el ministro de Economía, Axel Kicillof, para acompañarla en el agradecimiento que hará al mundo por la resolución contra los fondos buitre.",
                Category.POLITICA, "http://bucket1.glanacion.com/anexos/fotos/63/2096963w695tlnhaniiid.jpg", "http://www.lanacion.com.ar/1831436-la-presidenta-llega-hoy-a-nueva-york-para-hablar-en-la-onu"));

        this.mNewsArrayList.add(createNews("Macri dio señales de su eventual gabinete con gestos a Sanz y Carrió",
                "Ubicaría al jefe de la UCR en Justicia, a la diputada le daría una embajada europea y contaría con Zuvic para la tarea de combatir el narcotráfico; Ocaña, Aranguren, Triacca y Frigerio estarían en el equipo",
                "TANDIL.- El \"macrimóvil\" avanzaba por la irregular ruta nacional 226, que une Azul con esta ciudad, donde Mauricio Macri nació hace poco más de 56 años. Fingiendo cierto enojo, Ernesto Sanz se quejaba a los gritos \"de la discriminación a la que está sometido este militante de Cambiemos\" por no haber recibido ni siquiera un sandwichito. Mauricio Macri se reía de la ocurrencia y, desde un mullido sillón negro, respondió con una novedad. \"El ministro de Educación va a ser Esteban Bullrich. Y el de Justicia lo tienen acá, muy cerca\", dijo Macri, mientras el senador radical combinaba el silencio con una sonrisa cómplice.<br><br><br><br>Relajado como pocas veces, y optimista sobre sus chances en la pelea presidencial, Mauricio Macri dio así algunas pistas sobre su eventual gabinete si es que triunfa en las elecciones presidenciales del próximo 25 de octubre.<br><br><br><br>Las pistas sobre su eventual equipo de trabajo fueron alimentadas por sus asesores durante el viaje a Tandil. En esa lista de hipotéticos colaboradores, sobresale la ex ministra Graciela Ocaña, cercana a Horacio Rodríguez Larreta y quien sería encargada del área de Salud; el ex presidente de Shell Juan José Aranguren, que iría a Energía; Rogelio Frigerio, en Economía; Jorge Triacca, en Trabajo; Mariana Zuvic, en Narcotráfico, y Elisa Carrió, como \"embajadora en Europa\", según adelantó el propio Macri.<br><br><br><br>¿La Cancillería? \"No lo tenemos definido, pero claramente es una prioridad. De Uruguay en adelante no nos quedan más amigos\", criticó Macri a la gestión de relaciones exteriores del oficialismo desde el motorhome con cinco camas, dos amplios sillones, pantalla LED y aire acondicionado con el que recorre el país.<br><br>Tal vez por jugar de local, el líder de Pro combinó recuerdos personales con definiciones de campaña. Luego de rememorar la primera y única vez que fumó un cigarrillo, Macri recordó la inauguración que su tío Jorge Blanco Villegas hizo del hospital de Tandil, hace unos cinco años. Aquella vez, compartió el escenario con su rival de hoy, Daniel Scioli, el gobernador de Buenos Aires. \"A mí me ovacionaron, con él hicieron silencio\", dijo. Cuando LA NACION le recordó que \"en ese entonces se llevaban bien\", Macri se puso más serio: \"No sé si se liberó ahora, pero cambió mucho\". Y lo criticó por esquivar el debate de candidatos previsto para el 4 del mes próximo (ver aparte).<br><br>\"No es verdad que tengan 40 puntos, como tampoco era verdad que tenían 45, como decían. La mayoría quiere un cambio\", especuló Macri sobre la posibilidad de un eventual ballotage. ¿Y si gana Aníbal Fernández en provincia y usted la presidencia? \"Confío en el instinto de supervivencia de los bonaerenses\", bromeó el jefe de gobierno porteño.<br><br>Con Sergio Massa tampoco hay demasiado lugar para los mensajes amables. \"No hay diálogo\", dijo, seco y terminante. Aunque sí reconoció que habló con su aliado, José Manuel de la Sota. Con el gobernador cordobés se juntaron el lunes pasado para acordar una tregua y bajar \"el nivel de agresión\". Ambos coincidieron en \"que lo peor que le puede pasar al país es que siga este gobierno\", según relató Macri.<br><br>Esbozó la posibilidad de un \"acuerdo el 26 de octubre\" tanto con Massa como con los \"gobernadores serios del peronismo\". Sanz agregó a la lista a los radicales Ricardo Colombi y Alfredo Cornejo. Emilio Monzó, ministro de Gobierno porteño, bromeó: \"Y María Eugenia Vidal\". La intervención despertó una sonrisa en la candidata a gobernadora de Buenos Aires.<br><br>En Azul, como lo hizo en Olavarría y lo haría después en Tandil, Macri combinó caravanas por los barrios más necesitados con el apoyo de los candidatos locales. En la parroquia San Francisco de Asís, de Azul, prometió \"completar la autovía 3 para cuidar y comunicar a los argentinos\". El jefe de Pro continuará hoy con su campaña en Pilar, en el norte bonaerense.<br><br><b>Galuccio y la Corte Suprema</b><br><br>Antes de bajarse del motorhome, con el que irá a Mar del Plata para una cena con empresarios, Macri dio más definiciones.<br><br> Prescindiría del titular de la AFIP, Ricardo Echegaray, y de la procuradora general de la Nación, Alejandra Gils Carbó. ¿Qué haría con Miguel Galuccio, titular de YPF? \"Es una empresa mixta\", se excusó.<br><br>Macri agregó que si llega a la Casa Rosada, no modificaría el número de integrantes de la Corte Suprema. \"Lo mejor es no cambiar\", dijo, en relación con los cinco miembros. Eso sí, el candidato presidencial evitó dar el nombre de su candidato para reemplazar al juez Carlos Fayt, que abandonará del máximo tribunal el 11 de diciembre después de un período conflictivo con el kirchnerismo.", Category.POLITICA, "http://bucket3.glanacion.com/anexos/fotos/15/2096915h765.jpg", "http://www.lanacion.com.ar/1831406-macri-dio-senales-de-su-eventual-gabinete-con-gestos-a-sanz-y-carriosiguen-los-cruces-por-el-debate"));

        this.mNewsArrayList.add(createNews("Massa fustigó con dureza a sus rivales directos",
                "Presentó sus metas de gobierno en Neuquén, donde también criticó a Macri y Scioli",
                "Rodeado por sus economistas y con un discurso que priorizó la promesa de eliminar el impuesto a las ganancias, recuperar las jubilaciones y reactivar las economías regionales, Sergio Massa desembarcó ayer en Neuquén para presentar las metas de gobierno del frente UNA. Pero, ante la consulta de los medios locales, se salió de ese libreto y disparó contra sus rivales: identificó a Mauricio Macri con el regreso a los 90 y puso en duda la capacidad de liderazgo de Daniel Scioli frente a la influencia de Cristina Kirchner.<br><br>\"El cambio que propone Mauricio Macri es una invitación al pasado. Es el ajuste\", apuntó el tigrense, que vinculó al candidato de Cambiemos con el ex ministro de Economía Domingo Cavallo. \"Nosotros lo que les planteamos a los argentinos es que hay tres países en juego: el país de Roberto Lavagna, el país de Axel Kicillof y el país de Domingo Cavallo\", señaló.<br><br>Así, Massa retomó el repertorio de críticas que destinaba a Macri antes de que estallara el escándalo de las millonarias contrataciones de la Ciudad con Fernando Niembro y la posterior renuncia del candidato a diputado de Cambiemos. Fue, quizá, por la reunión que días atrás mantuvieron el líder de Pro y el gobernador de Córdoba, José Manuel de la Sota, donde hablaron de \"bajar el tono\" a los cruces verbales entre macristas y massistas. O, tal vez, por el peso creciente de la agenda económica en la campaña.<br><br>Ante la consulta por el debate de candidatos el tigrense volvió a pedir que la Presidenta \"autorice\" a Scioli a presentarse a la cita. Pero fue más allá y puso en duda el liderazgo del bonaerense si le toca llegar a la presidencia. \"¿Dónde va a estar la Casa Rosada el 11 de diciembre si sigue gobernando el Frente para la Victoria? No puede haber dos jefes, el doble comando te lleva a los fracasos\", alertó, en diálogo con radio La Red. Se amparó en los dichos de los dirigentes kirchneristas que plantearon un eventual gobierno de Scioli como la \"transición\" para el regreso, en 2019, de Cristina Kirchner.<br><br>Como sucederá hoy en Córdoba, cuando vuelva a mostrarse junto a De la Sota y el economista Roberto Lavagna, Massa presentó ayer sus metas de gobierno. Fue en el Museo Nacional de Bellas Artes de Neuquén, a donde acudió junto a los economistas Aldo Pignanelli y Ricardo Delgado, y el diputado y ex titular de la UIA, José Ignacio de Mendiguren. Como anfitrión se movió el senador Guillermo Pereyra, secretario adjunto de la CGT opositora y líder de los trabajadores petroleros y del gas privado de Neuquén, Río Negro y La Pampa.<br><br>Con esos socios, el discurso de Massa apuntó a la economía. Prometió eliminar el impuesto a las ganancias a los trabajadores y las trabas a la producción local. \"No podemos quedarnos de brazos cruzados mientras vemos cómo las manzanas se pudren arriba de los árboles, los productores se funden y se disuelven miles de empresas familiares. Los impuestos, el costo del flete, la caída de los precios internacionales amenazan cientos de miles de fuentes de trabajo\", afirmó.<br><br>También insistió en que los 500.000 millones de pesos del fondo de sustentabilidad de la Anses -y la rentabilidad que genera- permiten pagar el 82% móvil a los jubilados y los juicios adeudados por el Estado. \"Es una vergüenza que en la Argentina un preso gane más que un jubilado\", concluyó.<br><br>Los economistas no sólo presentaron las metas ajustadas a las necesidades de la región. También se metieron en la disputa política con el macrismo. \"Creo que hoy la propuesta del Frente Renovador es muy clara. Ya se sabe que está Lavagna o De la Sota y todavía el PRO no muestra sus actores. Nos gustaría saber si [el ministro de Economía] es Melconian o Sturzenegger\", chicaneó De Mendiguren.<br><br>\"Vamos a mantener el diferencial entre el precio interno y el internacional del petróleo, para favorecer las nuevas inversiones y generar más empleo en el sector. Nuestra propuesta de infraestructura generará inversiones en gas y petróleo del orden de los 20.000 millones de dólares anuales\", prometió Delgado, que dentro del equipo de Massa se especializó en elaborar el plan de infraestructura y energía.", Category.POLITICA, "http://bucket2.glanacion.com/anexos/fotos/21/2096921w300.jpg", "http://www.lanacion.com.ar/1831407-massa-fustigo-con-dureza-a-sus-rivales-directos"));

        this.mNewsArrayList.add(createNews("Kicillof analiza un canje para el Boden 2015, pero teme que no sea aceptado en el exterior",
                "A través de un grupo de bancos sondeó, sin demasiado éxito, a grandes fondos internacionales; muchos ahorristas locales también elegirían cobrar los dólares",
                "<br><br>" +
                        "\"Nos están pidiendo desesperadamente eso [por un canje], porque la gente que invierte en la Argentina, que tiene ese bono, quiere seguir teniendo bonos. Entonces nos lo están pidiendo. Vamos a ver las condiciones y la situación, porque con las medidas financieras conviene no generar expectativas\", sostuvo Kicillof en declaraciones radiales.<br><br>" +
                        "Pero la operación no sería sencilla. Al menos, según confiaron a LA NACION ejecutivos de fondos del exterior, el Gobierno ya estuvo en los últimos días sondeando sin demasiado éxito a través de bancos extranjeros el apetito de los grandes inversores internacionales por un canje de bonos.<br><br>" +
                        "\"Después de una devaluación como la que hubo en toda la región, lo que viene es una suba de tasas. Nadie va a canjear\", explicó un operador de una entidad internacional. \"Además, todavía no está claro si los Bonar 24, que son los bonos que probablemente podría ofrecer Economía, quedan fuera de la posibilidad de embargos por parte de los holdouts\", agregó, con la condición de no ser identificado<br><br>" +
                        "De acuerdo con fuentes del mercado, más del 45% de la emisión del Boden estaría en poder de inversores en el exterior. La cifra no es menor e inquieta a las autoridades, ya que son dólares que no volverán a las reservas del BCRA, a diferencia de lo que podría ocurrir con los tendores locales del bono, que podrían optar por mantener sus divisas en una cuenta en algún banco local.<br><br>" +
                        "Existe, asimismo, una gran cantidad de deuda en poder de individuos y empresas, que tampoco tendrían demasiado interés de canjear. Ante la imposibilidad de acceder al mercado de cambios oficial, muchos de ellos venían comprando Boden 2015 a lo largo de los últimos meses con el único objetivo de poder hacerse de dólares billete, y en blanco, al vencimiento de los bonos<br><br>" +
                        "Un canje de deuda apuntaría casi en forma exclusiva a inversores institucionales locales, muchos de los cuales prefieren tener bonos en moneda extranjera antes que los dólares billete. Ya que temen, más aún después de la decisión de la Comisión Nacional de Valores (CNV) de modificar la contabilidad e los fondos comunes (ver aparte), que en algún momento el Gobierno los obligue a vender sus tenencias al tipo de cambio oficial de 9,42 pesos.<br><br>" +
                        "\"La intención del canje está -aseguró a LA NACION un hombre cercano al ministro Kicillof-. Hay que ver si las tasas del mercado lo permiten.\"<br><br>" +
                        "Así las cosas, será difícil para Economía evitar que el pago de Boden ponga presión a las reservas del Banco Central. Amilcar Collante, economista del Centro de Estudios Económicos del Sur, estima que de los US$ 33.301 millones de reservas informadas por el BCRA, unos US$ 11.000 millones ya corresponden al préstamo acordado con China, cuyo primer tramo empieza a vencer en octubre. A su vez, dice, habría otros US$ 8200 millones que son en realidad depósitos de terceros que están en el sistema financiero. Con lo cual, las reservas disponibles sumarían US$ 14.000 millones y todo sin contemplar la deuda de US$ 8100 millones del BCRA con importadores.<br><br>" +
                        "Kicillof, no obstante, refutó ayer las versiones de que el Gobierno podría pesificar los pagos del Boden. \"Nadie en el mercado , nadie en su sano juicio tiene la menor duda de que se va a pagar el Boden 2015\", dijo.<br><br>" +
                        "El BCRA, por su parte, aprovechó en los últimos meses para comprar Boden 15 y así neutralizar el impacto de parte de los pagos. Fuentes que siguen de cerca el movimiento de la entidad, aseveran que habría logrado hacerse de bonos por poco más de US$ 400 millones.<br><br>" +
                        "La consultora Ecolatina calcula que la totalidad del vencimiento de Boden son US$ 6525 millones (el 40% de las reservas disponibles), que se suman a otros US$ 675 millones que se deben pagar en la misma semana por un bono de la provincia de Buenos Aires.<br><br>" +
                        "\"", Category.ECONOMIA, "http://bucket2.glanacion.com/anexos/fotos/31/2096931w225tlnhan.jpg", "http://www.lanacion.com.ar/1831414-kicillof-analiza-un-canje-para-el-boden-2015-pero-teme-que-no-sea-aceptado-en-el-exterior"));

        this.mNewsArrayList.add(createNews("Edgardo Bauza: \"Yo me río cuando me dicen que San Lorenzo es defensivo\"", "Un mano a mano con el entrenador del equipo que sigue detrás de Boca y al acecho de un nuevo título; \"En esta última etapa, lo más importante es la cabeza\", resulta su mensaje",
                "Un recorrido largo en años, kilómetros y experiencias convirtió a Edgardo Bauza en algo parecido a un trotamundos al que difícilmente algún ámbito en particular haga sentirse ajeno. La vida de concentraciones en hoteles cinco estrellas, naturalizada en el profesionalismo del fútbol actual, por ejemplo. \"A esta altura, ni me preocupa. Yo también viví la otra. Cuando empecé a concentrarme era otra historia. Con Independiente (donde jugó en los 80) nos concentrábamos frente a la estación Constitución, en el Palace. ¿Sabés lo que era eso? Por un lado, esto es mejor: el jugador está cómodo, tiene una habitación y una cama para descansar bien. En mi época había que cambiar las camas, tirar los colchones al piso. Desde lo económico, esto es más erogación para el club, pero está acorde con la evolución que tuvo el fútbol. Si algo me enseñó esta profesión es a convivir con ambas cosas. Yo nací en una familia de clase media baja, mis viejos laburaron toda su vida. Compraron una casa con un crédito hipotecario a 30 años. Siempre me transmitieron un deseo de superación permanente. Y con el tiempo me acostumbré a estas cosas. Así que esto no me causa ni me cambia nada. Sólo me interesa que el jugador descanse bien\", explica, sentado en uno de los salones del lujoso alojamiento del plantel de San Lorenzo, en San Telmo. El Patón había aparecido unos minutos antes con toda puntualidad, manos en los bolsillos y una sonrisa tranquila que, según se sincera, esconde la tensión propia de los momentos en que hay mucho en juego.<br><br><b>-¿San Lorenzo ya es una referencia sentimental para usted?<(b><br><br>-Hay un compromiso. El primer compromiso, más que con el club, fue con el plantel y los dirigentes. Fue recíproco, para buscar la mejor propuesta para un plantel excelente. Se fue transformando en un equipo para jugar en donde sea. Costó mucho. Una idea tiene un origen en el discurso y una recepción. Pero si no hay resultados queda inconclusa. El trabajo nos hizo crecer.<br><br><b>-Es su famosa idea del equilibrio o la solidez. ¿Qué son equilibrio y solidez?<b><br><br>-Una cosa es el equilibrio y otra, la solidez. Un equipo puede ser sólido y sin equilibrio porque se dedica a defenderse. Hubo muchos así en la historia. Pero atacan mal, o no atacan. El equilibrio es eso: defender y atacar con las mismas posibilidades. O con las posibilidades que un plantel da. Yo tengo que tener en cuenta otras cuestiones, como el promedio de edad de este plantel. Eso es determinante para elegir una táctica. Yo no puedo someter a Yepes, con 39 años, a que juegue con 50 metros en la espalda. O a Mercier y Ortigoza a que presionen constantemente.<b>-¿Pero no se puede caer en no ser ni una cosa ni la otra?<br><br>-Sí, claro. Toda idea puede interrumpirse, cortarse. Si no hubiesen llegado resultados no estaríamos hablando acá. Habría tenido que irme.",
                Category.DEPORTES, "http://bucket1.clanacion.com.ar/anexos/fotos/30/torneo-de-30-equipos-2096930w620.jpg", "http://canchallena.lanacion.com.ar/1831217-bauza-mi-mujer-me-pide-que-no-ponga-noticieros"));
        newsNotification = createNews("Choque en panamericana", "Choque a la altura de marquez, hay heridos", "Choque a la altura de marquez, hay heridos", Category.SOCIEDAD, "http://bucket1.clanacion.com.ar/anexos/fotos/30/torneo-de-30-equipos-2096930w620.jpg\",\"http://canchallena.lanacion.com.ar/1831217-bauza-mi-mujer-me-pide-que-no-ponga-noticieros", "http://bucket1.clanacion.com.ar/anexos/fotos/30/torneo-de-30-equipos-2096930w620.jpg\",\"http://canchallena.lanacion.com.ar/1831217-bauza-mi-mujer-me-pide-que-no-ponga-noticieros");
        this.mTextToSpeech = new TextToSpeech(this.mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                mTextToSpeech.setLanguage(mLocale);

                mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        if (s.equals("null")) {
                            Tattu.post(new SpeakStarted());
                        } else {
                            Tattu.post(new NewsStarted());
                        }
                    }
                    @Override
                    public void onDone(String s) {
                        if (s.equals("null")) {
                            Tattu.post(new SpeakEnded());
                        } else {
                            Tattu.post(new NewsEnded());
                        }
                    }

                    @Override
                    public void onError(String s) {
                    }
                });
            }
        });
    }

    public ArrayList<News> getNews() {
        return searchNewsCategory();

    }
    private ArrayList<News> searchNewsCategory() {
        ArrayList<News> newsList = new ArrayList<>();
        final ArrayList<Category> categories = new ArrayList<>(DataStore.getInstance().getAll(Category.class));
        int size = categories.size();
        int sizeNew = mNewsArrayList.size();
        for (int i = 0; i < size; i++) {
            Category category = categories.get(i);
            for (int it = 0; it < sizeNew; it++) {
                News news = mNewsArrayList.get(it);
                if (category.equals(news.category)) {
                    newsList.add(news);
                }
            }

        }
        return newsList;

    }
    /**
     * @return true si paro el servicio , sino devuevel false
     */
    public boolean stopSpeech() {
        if (mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();

            return true;
        } else {
            return false;
        }
    }

    public void read(News news) {
        read(news, false);
    }

    public void read(News news, boolean title) {
        speak(title ? news.title : news.subTitle, news.title);
    }

    public void speak(String text, String unique) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
        hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, unique != null ? unique : "null");
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, hashMap);
    }

    private News createNews(String title, String subtitle, String contenido, Category category, String imageUri, String url) {
        News news = new News();
        news.title = title;
        news.subTitle = subtitle;
        news.category = category;
        news.content = contenido;
        news.imageUri = imageUri;
        news.urlNews = url;
        return news;
    }

    public class NewsStarted {
    }

    public class NewsEnded {
    }

    public class SpeakStarted {

    }

    public class SpeakEnded {

    }

}
