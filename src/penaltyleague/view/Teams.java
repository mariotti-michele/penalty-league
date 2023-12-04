package penaltyleague.view;

import java.awt.image.BufferedImage;
import java.io.File;

public class Teams {
    private static final String[] TEAMS_NAMES = {"psg", "bayern_monaco", "tottenham", "milan", "club_brugge", "benfica",
            "borussia_dortmund", "chelsea", "liverpool", "real_madrid", "napoli", "eintracht_francoforte",
            "manchester_city", "lipsia", "inter", "porto"};
    private BufferedImage[] teamsLogos;
    private static final int[] TEAMS_RATINGS = {5, 5, 4, 4, 2, 3, 3, 5, 4, 5, 4, 2, 5, 3, 4, 3};

    private BufferedImage[][] shooterImg;

    private static final int NUMBER_OF_FRAMES = 4;

    public Teams() {
        loadImages();
    }

    private void loadImages() {
        this.teamsLogos = new BufferedImage[TEAMS_NAMES.length];
        this.shooterImg = new BufferedImage[TEAMS_NAMES.length][NUMBER_OF_FRAMES];

        for (int i = 0; i < TEAMS_NAMES.length; i++) {
            teamsLogos[i] = null;
            try {
                this.teamsLogos[i] = ResourceManager.loadImage("logos" + File.separator + TEAMS_NAMES[i] + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int j = 0; j < shooterImg[i].length; j++){
                try {
                    this.shooterImg[i][j] = ResourceManager.loadImage(
                            "shooter" + File.separator + "shooter_" + TEAMS_NAMES[i] + "_" + (j+1) + ".png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected int getNumberOfTeams(){
        return TEAMS_NAMES.length;
    }

    protected String getTeamName(int teamIndex){
        return TEAMS_NAMES[teamIndex];
    }

    protected BufferedImage getTeamLogo(int teamIndex){
        return teamsLogos[teamIndex];
    }

    protected int getTeamRating(int teamIndex){
        return TEAMS_RATINGS[teamIndex];
    }

    protected BufferedImage getShooterFrame(int teamIndex, int frame){
        return shooterImg[teamIndex][frame];
    }

    protected int getNumberOfFrames(){
        return NUMBER_OF_FRAMES;
    }

}