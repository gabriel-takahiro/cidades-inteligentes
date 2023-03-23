// obtém todos os links da navbar
const navbarLinks = document.querySelectorAll(".navbar ul li ul li a, .ativa");

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
        }
      });
    }
  });
});
