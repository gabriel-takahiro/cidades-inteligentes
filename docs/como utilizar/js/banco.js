// obtém todos os links da navbar
const navbarLinks = document.querySelectorAll(".navbar ul li ul li a[id], .ativa");
const navbarLinks2 = document.querySelectorAll(".navbar ul li ul li a[id]");
const navbarLinks3 = document.querySelectorAll(".navbar ul li ul li a[class]");

// percorre cada link e adiciona um listener de evento
navbarLinks.forEach(link => {
    link.addEventListener("click", function (event) {
        event.preventDefault(); // previne o comportamento padrão do link
        const href = link.getAttribute("href"); // obtém o valor do atributo href
        const section = document.querySelector(href); // encontra a seção com o id correspondente
        const sectionTop = section.offsetTop; // obtém a posição da seção em relação ao topo da página

        window.scrollTo({
            top: sectionTop,
            behavior: "smooth" // torna a animação suave
        });

        // adiciona a classe "atual" ao link da navbar que corresponde à seção atual
        navbarLinks.forEach(navLink => {
            navLink.classList.remove("atual");
        });
        link.classList.add("atual");

        if (link.getAttribute("class") === "ativa atual") {
            navbarLinks2.forEach(navLink => {
                const subLista = navLink.nextElementSibling;
                if(subLista != null){
                    subLista.classList.add("inicial");
                }
            });
        }
    });
});

navbarLinks2.forEach(link => {
    link.addEventListener("click", function (event) {
        navbarLinks2.forEach(navLink => {
            const subLista = navLink.nextElementSibling;
            if(subLista != null){
                subLista.classList.add("inicial");
            }
        });

        navbarLinks3.forEach(navLink => {
            navLink.classList.remove("atual");
        });

        const subLista = link.nextElementSibling;
        if(subLista != null){
            subLista.classList.remove("inicial");
        }
    });
});

navbarLinks3.forEach(link => {
    link.addEventListener("click", function (event) {
        event.preventDefault(); // previne o comportamento padrão do link
        const href = link.getAttribute("href"); // obtém o valor do atributo href
        const section = document.querySelector(href); // encontra a seção com o id correspondente
        const sectionTop = section.offsetTop; // obtém a posição da seção em relação ao topo da página

        window.scrollTo({
            top: sectionTop,
            behavior: "smooth" // torna a animação suave
        });

        navbarLinks3.forEach(navLink => {
            navLink.classList.remove("atual");
        });
        link.classList.add("atual");
    });
});

// monitora o scroll da página
window.addEventListener("scroll", function () {
    // obtém a posição atual do scroll
    const scrollPosition = window.pageYOffset;

    // percorre cada seção da página
    const sections = document.querySelectorAll("section[id]");
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.offsetHeight;
        const sectionId = section.getAttribute("id");

        // verifica se a posição atual do scroll está dentro da seção
        if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
            // adiciona a classe "ativo" ao link da navbar que corresponde à seção atual
            navbarLinks.forEach(link => {
                if (link.getAttribute("href") === `#${sectionId}`) {
                    navbarLinks.forEach(navLink => {
                        navLink.classList.remove("atual");
                    });
                    link.classList.add("atual");

                    if (link.getAttribute("class") === "ativa atual") {
                        navbarLinks2.forEach(navLink => {
                            const subLista = navLink.nextElementSibling;
                            if(subLista != null){
                                subLista.classList.add("inicial");
                            }
                        });
                    }
                }
            });

            navbarLinks2.forEach(link => {
                if (link.getAttribute("href") === `#${sectionId}`) {
                    navbarLinks2.forEach(navLink => {
                        const subLista = navLink.nextElementSibling;
                        if(subLista != null){
                            subLista.classList.add("inicial");
                        }
                    });

                    navbarLinks3.forEach(navLink => {
                        navLink.classList.remove("atual");
                    });

                    const subLista = link.nextElementSibling;
                    if(subLista != null){
                        subLista.classList.remove("inicial");
                    }
                }
            });
        }
    });

    const sectionsClass = document.querySelectorAll("section[class]");
    sectionsClass.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.offsetHeight;
        const sectionClass = section.getAttribute("class");

        // verifica se a posição atual do scroll está dentro da seção
        if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
            // adiciona a classe "ativo" ao link da navbar que corresponde à seção atual
            navbarLinks3.forEach(link => {
                if (link.getAttribute("href") === `.${sectionClass}`) {
                    navbarLinks3.forEach(navLink => {
                        navLink.classList.remove("atual");
                    });
                    link.classList.add("atual");

                    navbarLinks2.forEach(navLink => {
                        const subLista = navLink.nextElementSibling;
                        if(subLista != null){
                            subLista.classList.add("inicial");
                        }
                    });

                    const ul = link.parentNode.parentNode;
                    if(ul != null){
                        ul.classList.remove("inicial");
                    }
                    navbarLinks.forEach(navLink => {
                        navLink.classList.remove("atual");
                    });
                    const aElement = ul.parentNode.querySelector('a');
                    aElement.classList.add("atual");
                }
            });
        }
    });
});

function moverSuave(event) {
    event.preventDefault();

    // Obtém o ID do elemento de destino a partir do atributo "href" da âncora
    const idDestino = event.target.getAttribute('href').slice(1);

    // Obtém a posição vertical do elemento de destino
    const secaoDestino = document.getElementById(idDestino);
    const sectionTop = secaoDestino.offsetTop;

    // Executa a função "scrollTo" para rolar suavemente até o elemento de destino
    window.scrollTo({
        top: sectionTop,
        behavior: 'smooth'
    });
}