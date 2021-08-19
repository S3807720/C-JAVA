//https://greensock.com/forums/topic/26001-animate-all-elements-with-a-specific-class-using-scrolltrigger-with-stagger/
const scrollElements = gsap.utils.toArray('.cs');
scrollElements.forEach(element => {
    gsap.set(element, {
        opacity: 0,
        y: 24
    });

    element.anim = gsap.to(element, {
        paused: true,
        opacity: 1,
        y: 0,
    });
});

function staggerPlayBatch(batch) {
    gsap.to(batch, {
        stagger: {
            each: 1,
            onStart: function() { this.targets()[0].anim.play() }
        }
    })
}

function staggerReverseBatch(batch) {
    gsap.to(batch, {
        stagger: {
            each: 1,
            onStart: function() { this.targets()[0].anim.reverse() }
        }
    })
}
ScrollTrigger.batch(scrollElements, {
    onEnter: staggerPlayBatch,
    onLeave: staggerReverseBatch,
    onEnterBack: staggerPlayBatch,
    onLeaveBack: staggerReverseBatch
});

//enhance the parallex effect
$(document).ready(function() {
    $(window).scroll(function(e) {
        parallax();
    });

    function parallax() {
        var scrolled = $(window).scrollTop();
        //increase scroll speed as the user scrolls
        $('.hero').css('top', -(scrolled * 0.035) + 'rem');
        $('.description').css('top', -(scrolled * 0.035) + 'rem');
        //reduce opacity slowly to give a nice fading effect
        $('#about').css('opacity', 1 - (scrolled * .00125));
    };
});


var slideIndex = 1;

showSlides(slideIndex);
// Next/previous controls
function plusSlides(n) {
    showSlides(slideIndex += n);
}

// Thumbnail image controls
function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("slide");
    if (n > slides.length) { slideIndex = 1 }
    if (n < 1) { slideIndex = slides.length }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
}





//drawer for mobile
openButton.addEventListener('click', () => drawer.show());
closeButton.addEventListener('click', () => drawer.hide());

const form = document.querySelector('.input-validation-type');
form.addEventListener('sl-submit', () => document.getElementById('confirmation').innerHTML = "Your message has been received.");