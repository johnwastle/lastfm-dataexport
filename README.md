lastfm-dataexport
=================
Execute LastFmExportTracksToTabDelimitedFile to export last.fm tracks to a tab-delimited text file.

This code example writes:
    Artist
    Track name
    Album name
    Scrobble timestamp

Assuming you have a MySQL table as follows:

    CREATE TABLE `lastfm`.`tracks` (
    `artist` varchar(255)  NOT NULL,
    `track` varchar(255)  NOT NULL,
    `album` varchar(255)  NOT NULL,
    `ts` timestamp  NOT NULL,
           KEY artist (artist),
           KEY track (track),
           KEY album (album),
           KEY ts (ts)
    )

You can load data from the 'tracks.tsv' file with

    LOAD DATA LOCAL INFILE 'tracks.tsv' INTO TABLE tracks FIELDS TERMINATED BY '\t' ENCLOSED BY '"' ;


##Known issues

Track names that include backslashes need to be escaped.

'/ / M \ \' by Health is the only example I can find though.

http://www.last.fm/music/Health/_/%2F%2FM%5C%5C


    select * from tracks where track like '/ / M%';
    update tracks set track='/ / M \\ \\', album= 'HEALTH' where track like '/ / M%';
