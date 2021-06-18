// "Interactive Smoke Effect With Javascript And Canvas - Smoke.Js | CSS Script". CSS Script, 2021. https://www.cssscript.com/interactive-smoke-effect-javascript-canvas-smoke-js/.
function smoke(intensity) {
  var canvas = document.getElementById('smoke');
  var ctx = canvas.getContext('2d');
  canvas.width = 1000;
  canvas.height = 1000;

  var party = SmokeMachine(ctx, [255,255,255]);

  party.start(); // start animating

  party.addSmoke(500,500,10*intensity); // wow we made smoke

  setTimeout(function(){

    party.stop(); // stop animating
    party.addSmoke(600,500,100*intensity);
    party.addSmoke(500,600,20*intensity);

    for(var i=0;i<10;i++){
      party.step(10); // pretend 10 ms pass and rerender
    }

    setTimeout(function(){
      party.start();
    },1000);

  },1000);

}
//show form and hide results
function showInput() {
  document.getElementById("form").style.display="block";
  document.getElementById("numCoffees").style.display="block";
  document.getElementById("calculate").style.display="block";
  document.getElementById("results").style.visibility="hidden";
}
//hide the form and display the results parts
function showResults() {
  document.getElementById("form").style.display="none";
  document.getElementById("numCoffees").style.display="none";
  document.getElementById("results").style.visibility="visible";
}
//do all the mathy stuff here
function calculate() {
  //copy days element to local
  var coffeesPerDay = document.getElementsByClassName("day");
  //set cheapest variable and check if it'll parse as an int, if so
  //set it as that otherwise keep it as 0. should error but bleh
  //do same for expensive coffee
  var cheapestCoff = 0;
  if (parseInt(document.getElementById("cheapCoff").value)) {
    cheapestCoff = parseInt(document.getElementById("cheapCoff").value);
  }
  var expCoff = 0;
  if (parseInt(document.getElementById("expCoffee").value)) {
    expCoff = parseInt(document.getElementById("expCoffee").value);
  }
  //get num of coffees.
  //convert array into temp variable
  //then add to numCoffees counter as an int
  var numCoffees = 0;
  for (var i = 0; coffeesPerDay.length > i; i++) {
    let temp = coffeesPerDay[i];
    numCoffees+= parseInt(temp.value);
  }
  //now process it all into the html
  processResults(numCoffees, cheapestCoff, expCoff);
}

function processResults(numCoffees, cheapestCoff, expCoff) {
  //temp var for average cost.
  let avgCost = ( (cheapestCoff + expCoff) / 2 ).toFixed(2);
  let monthlyCoff = numCoffees * 4;
  let yearlyCoff = numCoffees * 52;
  //set the results page with appropriate values
  document.getElementById("avgCoff").innerHTML = " Average Coffee Cost: " + avgCost;
  document.getElementById("coffPerYear").innerHTML = " Total Coffees per year: " + yearlyCoff;
  document.getElementById("coffPerMonth").innerHTML = " Total Coffees per month: " + monthlyCoff;

  document.getElementById("monthMostExp").innerHTML = " Drinking only most expensive: $" +(expCoff*monthlyCoff).toFixed(2);
  document.getElementById("monthCheapest").innerHTML = " Drinking only cheapest: $"+ (cheapestCoff*monthlyCoff).toFixed(2);
  document.getElementById("monthAvg").innerHTML = " Drinking average cost: $" + (avgCost * monthlyCoff).toFixed(2);

  document.getElementById("yearMostExp").innerHTML = " Drinking only most expensive: $" +(expCoff* yearlyCoff).toFixed(2);
  document.getElementById("yearCheapest").innerHTML = " Drinking only cheapest: $"+ (cheapestCoff* yearlyCoff).toFixed(2);
  document.getElementById("yearAvg").innerHTML = " Drinking average cost: $" + (avgCost * yearlyCoff).toFixed(2);
  //display stuff
  showResults();
  //set reactionary coffee image based on how much of an addict they are
  //and a previously made smoke/steam animation for fun, higher intensity based on above
  var elem = document.getElementById("coffImg");
  elem.setAttribute("height", "256");
  elem.setAttribute("width", "256");
  elem.setAttribute("alt", "coffee result");
  elem.setAttribute("src", "");
  if (monthlyCoff < 22) {
    /* Yap, Jeremy. "Photo By Jeremy Yap On Unsplash". Unsplash.Com, 2021. https://unsplash.com/photos/jn-HaGWe4yw. */
    elem.setAttribute("src", "images/coffeeNormal.png");
    document.getElementById("coffMessage").innerHTML = "Nothing abnormal here.";
    smoke(1);
  }else if (monthlyCoff < 80 && monthlyCoff > 21) {
    /* Carter, Jack. "Photo By Jack Carter On Unsplash". Unsplash.Com, 2021. https://unsplash.com/photos/ic-p_bohKCo. */
    elem.setAttribute("src", "images/coffeeAddict.png");
    document.getElementById("coffMessage").innerHTML = "Caffeine Addict Alert.";
    smoke(30);

  }else {
    /*Dumlao, Nathan. "Photo By Nathan Dumlao On Unsplash". Unsplash.Com, 2021. https://unsplash.com/photos/1eDx4hjhHFE.*/
    elem.setAttribute("src", "images/coffee.png");
    document.getElementById("coffMessage").innerHTML = "You are literally made of coffee.";
    smoke(100);
  }
}
